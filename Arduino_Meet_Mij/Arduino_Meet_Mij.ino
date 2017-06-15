 // Demo sketch for MPU-6050 Accelerometer + Gyro sensor
// connected to WeMos D1 mini (ESP8266)
// acting as a server and WiFi access point
// 
// Blueprint by Hans van der Linden - Avans Hogeschool
// 2017-05-28
// 
// Edited by Patrick Eversdijk
//
// Based on open source / public domain example code for MPU6050 and ESP8266
// This code is open source & public domain
// 
// This sketch reads sensor data from an MPU6050 accelerometer/gyro via I2C
// This sketch is for the WeMos D1 mini ESP8266 board

#include <ESP8266WiFi.h>
#include "MPU6050Support.h"
#include <Wire.h>
#include <Adafruit_BMP085.h>
 
Adafruit_BMP085 bmp;

// Define interval between samples
const unsigned long sampleInterval =50000L; // microseconds

unsigned long lastMicros;
unsigned long currentMicros;
int columnCounter = 0;
int seaLevelPressure = 0;
int height = 0;
float g_x, g_y, g_z, gyro_x, gyro_y, gyro_z, altitude;
float speed_x, speed_y, speed_z, speed_estimation;
float filter_x, filter_y, filter_z;

accel_temp_gyro_raw_t rawSensorData;
accel_gyro_temp_t sensorData;
accel_gyro_temp_t sensorDataLowPassFiltered;

const char* ssid = "MeetMijBabyOneMoreTime";
const char* password = "MeetMijPlease";

const int PORT = 8080;
WiFiServer server(PORT);


void write_data_to_serial() {
  //Serial.print(F("Meeting: x = "));
  //Serial.print(filter_x);
  //Serial.print(F(", y = "));
  //Serial.print(filter_y);
  //Serial.print(F(", z = "));
  //Serial.println(filter_z); 
  Serial.print(F("Speed: x = "));
  Serial.print(speed_x);
  Serial.print(F(", y = "));
  Serial.print(speed_y);
  Serial.print(F(", z = "));
  Serial.println(speed_z);
  Serial.print(F("ms:"));
  Serial.println(millis());

  //Serial.print(F("Altitude = "));
  //Serial.print(altitude);
  //Serial.println(F(" Meter"));
  
  Serial.print(F("speed = "));
  Serial.print(speed_estimation);
  Serial.println(F(" m/s"));
}

bool isGetRequest(String header) {
  return header.indexOf("GET") >= 0;
}

bool isPostRequest(String header) {
  return header.indexOf("POST") >= 0;
}

String prepareGetResponse(accel_gyro_temp_t* sensorData) {
  String response = "";
  response += "HTTP/1.1 200 OK\r\n";
  response += "Connection: close\r\n";
  response += "Content-Type: application/json\r\n";
  response += "\r\n";

  String json = "{\r\n";
  json += "\"speed\":";
  json += speed_estimation;
  json += ",\r\n";
  json += "\"altitude\":";
  json += altitude;
  json += ",\r\n";
  json += "\"millis\":";
  json += millis();
  json += "\r\n";
  json += "}\r\n";
  response += json;
  response += "\r\n";
  return response;
}

String preparePostResponse() {
  String response = "";
  response += "HTTP/1.1 200 OK\r\n";
  response += "Connection: close\r\n";
  response += "Content-Type: application/json\r\n";
  response += "\r\n";
  response += "{\"message\": \"Post request received\"}\r\n";
  response += "\r\n";
  return response;
}

void setup() {
  // Open the serial port
  
  Serial.begin(115200);

 if (!bmp.begin()) 
  {
    Serial.println("Could not find BMP180 or BMP085 sensor at 0x77");
    while (1) {}
  }
Serial.println("Calculating sealevel pressure... ");
  seaLevelPressure = bmp.readSealevelPressure(height);
  Serial.print("Sealevel pressure = ");
  Serial.println(seaLevelPressure);
 
  // Initialize the MPU6050
  Serial.print("Initializing MPU6050... ");
  if (setup_MPU6050() == 0)
  {
    Serial.println("MPU6050 initialized.");
  } else {
    Serial.println("MPU6050 failed!");
  }
  // Perform offset calibration
  if (zero_offset_MPU6050() == 0) {
    Serial.println("MPU6050 offset calibrated.");
  } else {
    Serial.println("MPU6050 offset calibration failed!");
  }
  // Show the offset
  write_data_to_serial(); // TODO add a parameter to this function (correct data)

  // Set up the access point for the smart phone to connect to
  WiFi.softAP(ssid, password);
  Serial.print("Access Point SSID: ");
  Serial.println(ssid);
  Serial.print("Access Point Password: ");
  Serial.println(password);
  Serial.print("Access Point IP address: ");
  IPAddress apIP = WiFi.softAPIP();
  Serial.println(apIP);

  server.begin();
  server.setNoDelay(true);
  Serial.println("Server is running...");

  Serial.setDebugOutput(true); // For debugging
  WiFi.printDiag(Serial); // For debugging
}

void loop() {
  int error;
  
  currentMicros = micros(); // Time elapsed since power up
  
  // Check whether another sample from the MPU6050 sensor should be taken
  if (currentMicros > lastMicros + sampleInterval) {
    columnCounter += 1;
    //Serial.print(".");
    if (columnCounter % 60 == 0) { Serial.println(); }
    
    // Calculate time for next sample
    lastMicros += sampleInterval;
    // Check for missed samples
    if (currentMicros > lastMicros + sampleInterval) {
      // We seem to have missed one or more samples
      Serial.println("Skipping sample(s)");
      lastMicros = currentMicros;
    }
    
    // First read the raw values from the sensor
    error = read_raw_MPU6050(&rawSensorData);
    if (error != 0) {
      Serial.println("Error reading MPU6050 sensor");
    }
    // Convert to usable values
    error = convert_raw_MPU6050(&sensorData, &rawSensorData);
    if (error != 0) {
      Serial.println("Error converting MPU6050 sensor data");
    }
    // Subtract offset
    error = subtract_offset_MPU6050(&sensorData);
    if (error != 0) {
      Serial.println("Error subtracting offset from MPU6050 sensor data");
    }
    // Low pass filter the sensor values to get rid of excess noise
    error = low_pass_filter_MPU6050(&sensorDataLowPassFiltered, &sensorData);
    if (error != 0) {
      Serial.println("Error low pass filtering MPU6050 sensor data");
    }
    
    // Send the data to the serial port (debugging)
    filter_x = sensorDataLowPassFiltered.accel_x / 8192.0f;
    filter_y = sensorDataLowPassFiltered.accel_y / 8192.0f;
    filter_z = sensorDataLowPassFiltered.accel_z / 8192.0f;
    if(filter_x > 0.02 || filter_x < -0.02){
    g_x = filter_x;}
    else{g_x=0;}
    if(filter_y > 0.02 || filter_y < -0.02){
    g_y = filter_y;}
    else{g_y=0;}
    if(filter_z > 0.02 || filter_z < -0.02){
    g_z = filter_z;}
    else{g_z=0;}
    
    gyro_x = sensorDataLowPassFiltered.gyro_x;
    gyro_y = sensorDataLowPassFiltered.gyro_y;
    gyro_z = sensorDataLowPassFiltered.gyro_z;
    
    altitude = bmp.readAltitude((float) seaLevelPressure);
    speed_x += g_x * 9.81 / 20;
    speed_y += g_y * 9.81 / 20;
    speed_z += g_z * 9.81 / 20;
    speed_estimation = sqrt(speed_x*speed_x + speed_y*speed_y + speed_z*speed_z); 
    write_data_to_serial();
  }
  
  delay(1); // give ESP8266 time for background tasks
  
  // Handle a client if there is one
  WiFiClient client = server.available();
  if (client) {
    Serial.println("Client found");
    String httpHeader = "";
    while (client.connected()) {
      Serial.println("Client connected");
      if (client.available()) {
        // Client has sent a request, so handle it
        String line = client.readStringUntil('\r');
        httpHeader += line;
        //Serial.print(line); // Show each line of the client request
        if (line.length() == 1 && line[0] == '\n') {
          // End of client request (= empty line) reached
          //Serial.println("End of client request");
          if (isGetRequest(httpHeader)) {
            Serial.println("Sending sensor data response to GET");
            client.println(prepareGetResponse(&sensorDataLowPassFiltered));
            break;
          } else if (isPostRequest(httpHeader)) {
           Serial.println("Sending dummy response to POST");
            client.println(preparePostResponse());
            break;
          } else {
            Serial.println("Unsupported request, not sending response");
          }
        }
      } else {
        // Nothing to read from the client
        break;
      }
    }
    client.flush(); // Is this needed?
    client.stop(); // Clean up the client connection
    Serial.println(millis());
  }
}

