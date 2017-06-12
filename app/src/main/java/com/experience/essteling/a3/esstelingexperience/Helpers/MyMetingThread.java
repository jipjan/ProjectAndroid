package com.experience.essteling.a3.esstelingexperience.Helpers;

import android.support.v4.util.DebugUtils;
import android.util.Log;

import com.experience.essteling.a3.esstelingexperience.Entities.Data;
import com.experience.essteling.a3.esstelingexperience.Entities.MetingenData;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lois Gussenhoven on 8-6-2017.
 */

public class MyMetingThread {

    private SensorData _sensorData;
    private Thread _sensorThread;
    private boolean _stop;
    private URL _url;

    public MyMetingThread(int id) {
        _sensorData = MetingenData.ITEMS.getListOrNew(id).newSensorData();

        try {
            _url = new URI("http://192.168.4.1:8080").toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }

        _sensorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!_stop) {
                    try {
                        String response = dataReader();
                        Data data = responseToObject(response);
                        if (data != null) {
                            Log.d("Error", "I succeed at life");
                            _sensorData.add(data);
                        }
                        else
                            Log.d("Error", "I suck at life");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        });
    }

    private String dataReader() throws IOException, URISyntaxException {
        StringBuilder response = new StringBuilder();
        URLConnection urlC = _url.openConnection();
        InputStreamReader input = new InputStreamReader(urlC.getInputStream());
        BufferedReader reader = new BufferedReader(input);
        String read;
        while ((read = reader.readLine()) != null)
            response.append(read);
        reader.close();
        input.close();
        return response.toString();
    }

    private Data responseToObject(String response) {
        if (response != null && response != "") {
            try {
                JSONObject obj = new JSONObject(response);
                double speed = obj.getDouble("speed");
                double height = obj.getDouble("altitude");
                long ms = obj.getLong("millis");
                return new Data(speed, height, ms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void start() {
        _sensorThread.start();
    }

    public SensorData stop() {
        _stop = true;
        _sensorThread = null;
        return _sensorData;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
