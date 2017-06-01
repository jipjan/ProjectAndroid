// MPU-6050 Accelerometer + Gyro
// -----------------------------
//
// By arduino.cc user "Krodal".
// June 2012
// Open Source / Public Domain
// Modified by Debra - http://www.geekmomprojects.com/gyroscopes-and-accelerometers-on-a-chip/
// Modified by Hans van der Linden - Avans Hogeschool
//
// Using Arduino 1.0.1
// It will not work with an older version, 
// since Wire.endTransmission() uses a parameter 
// to hold or release the I2C bus.
//
// Documentation:
// - The InvenSense documents:
//   - "MPU-6000 and MPU-6050 Product Specification",
//     PS-MPU-6000A.pdf
//   - "MPU-6000 and MPU-6050 Register Map and Descriptions",
//     RM-MPU-6000A.pdf or RS-MPU-6000A.pdf
//   - "MPU-6000/MPU-6050 9-Axis Evaluation Board User Guide"
//     AN-MPU-6000EVB.pdf
// 
// The accuracy is 16-bits.
//
// Temperature sensor from -40 to +85 degrees Celsius
//   340 per degrees, -512 at 35 degrees.
//
// At power-up, all registers are zero, except these two:
//      Register 0x6B (PWR_MGMT_2) = 0x40  (I read zero).
//      Register 0x75 (WHO_AM_I)   = 0x68.
// 

#include <Wire.h>
#include "MPU6050Defs.h"
#include "MPU6050Support.h"

// Default I2C address for the MPU-6050 is 0x68.
// But only if the AD0 pin is low.
// Some sensor boards have AD0 high, and the
// I2C address thus becomes 0x69.
#define MPU6050_I2C_ADDRESS 0x68

// Low pass filter coefficient for smoothing the raw data
// Low pass filter coefficient must be between 0 and low pass filter constant
#define LOW_PASS_FILTER_CONSTANT 8
#define LOW_PASS_FILTER_COEFFICIENT 2

// Offset for sensor values, determined during offset calibration
// TODO Make the offset variable a static global variable (no external linkage)
accel_gyro_temp_t offset;
#define NUMBER_OF_ZERO_OFFSET_MEASUREMENTS 20



// Use the following global variables and access functions to help store the overall
// rotation angle of the sensor
//unsigned long last_read_time;
float         last_x_angle;  // These are the filtered angles
float         last_y_angle;
float         last_z_angle;  
float         last_gyro_x_angle;  // Store the gyro angles to compare drift
float         last_gyro_y_angle;
float         last_gyro_z_angle;


// Conversion to degrees/second
const float FS_SEL = 131.0;
// Conversion to ...?
const float G_CONVERT = 16384.0;
const float RADIANS_TO_DEGREES = 180/3.14159;

// Filter constant
const float alpha = 0.96;

int setup_MPU6050()
{
  int error;
  uint8_t c;

  // Start the I2C interface, use the default pins
  Wire.begin(SDA, SCL);

  // default at power-up:
  //    Gyro at 250 degrees second
  //    Acceleration at 2g
  //    Clock source at internal 8MHz
  //    The device is in sleep mode.
  //

  // Identify the MPU6050
  error = MPU6050_read(MPU6050_WHO_AM_I, &c, 1);
  Serial.print(F("WHO_AM_I : "));
  Serial.print(c,HEX);
  Serial.print(F(", error = "));
  Serial.println(error,DEC);
  if (error != 0) return error;

  // According to the datasheet, the 'sleep' bit
  // should read a '1'. But I read a '0'.
  // That bit has to be cleared, since the sensor
  // is in sleep mode at power-up. Even if the
  // bit reads '0'.
  error = MPU6050_read(MPU6050_PWR_MGMT_2, &c, 1);
  Serial.print(F("PWR_MGMT_2 : "));
  Serial.print(c,HEX);
  Serial.print(F(", error = "));
  Serial.println(error,DEC);
  if (error != 0) return error;

  // Clear the 'sleep' bit to start the sensor.
  MPU6050_write_reg (MPU6050_PWR_MGMT_1, 0);

  // Set the sample rate divider
  MPU6050_write_reg (MPU6050_SMPLRT_DIV, 7);
  
  return error;
}

int read_raw_MPU6050(accel_temp_gyro_raw_t* data_ptr)
{
  // Read the raw 8-bit high and low values.
  // Read 14 bytes at once, 
  // containing acceleration, temperature and gyro.
  // With the default settings of the MPU-6050,
  // there is no filter enabled, and the values
  // are not very stable. 
  // Returns the error value
  int error = MPU6050_read(MPU6050_ACCEL_XOUT_H, (uint8_t*) data_ptr, sizeof(accel_temp_gyro_raw_t));
  return error;
}

int convert_raw_MPU6050(accel_gyro_temp_t* dest_ptr, accel_temp_gyro_raw_t* raw_ptr)
{
  // Convert from raw 8-bit values to 16-bit integers
  dest_ptr->accel_x = raw_ptr->accel_xl | (raw_ptr->accel_xh << 8);
  dest_ptr->accel_y = raw_ptr->accel_yl | (raw_ptr->accel_yh << 8);
  dest_ptr->accel_z = raw_ptr->accel_zl | (raw_ptr->accel_zh << 8);
  dest_ptr->gyro_x = raw_ptr->gyro_xl | (raw_ptr->gyro_xh << 8);
  dest_ptr->gyro_y = raw_ptr->gyro_yl | (raw_ptr->gyro_yh << 8);
  dest_ptr->gyro_z = raw_ptr->gyro_zl | (raw_ptr->gyro_zh << 8);
  dest_ptr->temp = raw_ptr->temp_l | (raw_ptr->temp_h << 8);
  return 0;
}

int zero_offset_MPU6050()
{
  int error;
  int counter;
  int measurements = 0;
  accel_temp_gyro_raw_t sensorDataRaw;
  accel_gyro_temp_t sensorData;
  // Determine offset to be applied to sensor data
  // by storing the initial sensor data
  Serial.print("Starting offset calibration... ");
  // Read and ignore the first couple of values read from the sensor
  for (counter = 0; counter < NUMBER_OF_ZERO_OFFSET_MEASUREMENTS; counter++) {
    error = read_raw_MPU6050(&sensorDataRaw);
    if (error != 0) {
      Serial.print("?");
      Serial.print(" error = ");
      Serial.println(error);
    } else {
      Serial.print(">");
    }
  }
  // Read and average a number of sensor values
  for (counter = 0; counter < NUMBER_OF_ZERO_OFFSET_MEASUREMENTS; counter++) {
    error = read_raw_MPU6050(&sensorDataRaw);
    if (error == 0) {
      error = convert_raw_MPU6050(&sensorData, &sensorDataRaw);
      if (error == 0) {
        offset.accel_x += sensorData.accel_x;
        offset.accel_y += sensorData.accel_y;
        offset.accel_z += sensorData.accel_z;
        offset.gyro_x += sensorData.gyro_x;
        offset.gyro_y += sensorData.gyro_y;
        offset.gyro_z += sensorData.gyro_z;
        measurements += 1;
        Serial.print(".");
      } else {
        Serial.print("-");
        Serial.print(" error = ");
        Serial.println(error);
      }
    } else {
      Serial.print("x");
      Serial.print(" error = ");
      Serial.println(error);
    }
  }
  if (measurements > 0) {
    offset.accel_x /= measurements;
    offset.accel_y /= measurements;
    offset.accel_z /= measurements;
    offset.gyro_x /= measurements;
    offset.gyro_y /= measurements;
    offset.gyro_z /= measurements;
    Serial.println();
    Serial.print("offset ax = "); Serial.print(offset.accel_x); Serial.println();
    Serial.print("offset ay = "); Serial.print(offset.accel_y); Serial.println();
    Serial.print("offset az = "); Serial.print(offset.accel_z); Serial.println();
    Serial.println("Done");
  } else {
    error = -1;
    Serial.println("Failed (no valid measurements)");
  }
  return error;
}

int subtract_offset_MPU6050(accel_gyro_temp_t* data_ptr)
{
  data_ptr->accel_x -= offset.accel_x;
  data_ptr->accel_y -= offset.accel_y;
  data_ptr->accel_z -= offset.accel_z;
  data_ptr->gyro_x -= offset.gyro_x;
  data_ptr->gyro_y -= offset.gyro_y;
  data_ptr->gyro_z -= offset.gyro_z;
  // Don't subtract temperature value since we don't have an offset for it
  return 0;
}

int low_pass_filter_MPU6050(accel_gyro_temp_t* lowpass_ptr, accel_gyro_temp_t* data_ptr)
{
  // Low pass filters the data with a very simple filter
  lowpass_ptr->accel_x = (data_ptr->accel_x * LOW_PASS_FILTER_COEFFICIENT +
    lowpass_ptr->accel_x * (LOW_PASS_FILTER_CONSTANT - LOW_PASS_FILTER_COEFFICIENT))
    / LOW_PASS_FILTER_CONSTANT;
  lowpass_ptr->accel_y = (data_ptr->accel_y * LOW_PASS_FILTER_COEFFICIENT +
    lowpass_ptr->accel_y * (LOW_PASS_FILTER_CONSTANT - LOW_PASS_FILTER_COEFFICIENT))
    / LOW_PASS_FILTER_CONSTANT;
  lowpass_ptr->accel_z = (data_ptr->accel_z * LOW_PASS_FILTER_COEFFICIENT +
    lowpass_ptr->accel_z * (LOW_PASS_FILTER_CONSTANT - LOW_PASS_FILTER_COEFFICIENT))
    / LOW_PASS_FILTER_CONSTANT;
  lowpass_ptr->gyro_x = (data_ptr->gyro_x * LOW_PASS_FILTER_COEFFICIENT +
    lowpass_ptr->gyro_x * (LOW_PASS_FILTER_CONSTANT - LOW_PASS_FILTER_COEFFICIENT))
    / LOW_PASS_FILTER_CONSTANT;
  lowpass_ptr->gyro_y = (data_ptr->gyro_y * LOW_PASS_FILTER_COEFFICIENT +
    lowpass_ptr->gyro_y * (LOW_PASS_FILTER_CONSTANT - LOW_PASS_FILTER_COEFFICIENT))
    / LOW_PASS_FILTER_CONSTANT;
  lowpass_ptr->gyro_z = (data_ptr->gyro_z * LOW_PASS_FILTER_COEFFICIENT +
    lowpass_ptr->gyro_z * (LOW_PASS_FILTER_CONSTANT - LOW_PASS_FILTER_COEFFICIENT))
    / LOW_PASS_FILTER_CONSTANT;
  lowpass_ptr->temp = data_ptr->temp; // No need to low pass filter the temperature
  return 0;
}

// --------------------------------------------------------
// MPU6050_read
//
// This is a common function to read multiple bytes 
// from an I2C device.
//
// It uses the boolean parameter for Wire.endTransMission()
// to be able to hold or release the I2C-bus. 
// This is implemented in Arduino 1.0.1.
//
// Only this function is used to read. 
// There is no function for a single byte.
//
int MPU6050_read(int start, uint8_t *buffer, int size)
{
  int i, n, error;

  Wire.beginTransmission(MPU6050_I2C_ADDRESS);
  n = Wire.write(start);
  if (n != 1)
    return (-10);

  n = Wire.endTransmission(false);    // hold the I2C-bus
  if (n != 0)
    return (n);

  // Third parameter is true: relase I2C-bus after data is read.
  Wire.requestFrom(MPU6050_I2C_ADDRESS, size, true);
  i = 0;
  while(Wire.available() && i<size)
  {
    buffer[i++]=Wire.read();
  }
  if ( i != size)
    return (-11);

  return (0);  // return : no error
}


// --------------------------------------------------------
// MPU6050_write
//
// This is a common function to write multiple bytes to an I2C device.
//
// If only a single register is written,
// use the function MPU_6050_write_reg().
//
// Parameters:
//   start : Start address, use a define for the register
//   pData : A pointer to the data to write.
//   size  : The number of bytes to write.
//
// If only a single register is written, a pointer
// to the data has to be used, and the size is
// a single byte:
//   int data = 0;        // the data to write
//   MPU6050_write (MPU6050_PWR_MGMT_1, &c, 1);
//
int MPU6050_write(int start, const uint8_t *pData, int size)
{
  int n, error;

  Wire.beginTransmission(MPU6050_I2C_ADDRESS);
  n = Wire.write(start);        // write the start address
  if (n != 1)
    return (-20);

  n = Wire.write(pData, size);  // write data bytes
  if (n != size)
    return (-21);

  error = Wire.endTransmission(true); // release the I2C-bus
  if (error != 0)
    return (error);

  return (0);         // return : no error
}

// --------------------------------------------------------
// MPU6050_write_reg
//
// An extra function to write a single register.
// It is just a wrapper around the MPU_6050_write()
// function, and it is only a convenient function
// to make it easier to write a single register.
//
int MPU6050_write_reg(int reg, uint8_t data)
{
  int error;

  error = MPU6050_write(reg, &data, 1);

  return (error);
}
