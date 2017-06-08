package com.experience.essteling.a3.esstelingexperience;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class DataHandler extends AsyncTask<String, Void, String> {

    private IDataListener _listener;

    public DataHandler(SensorDataList list, IDataListener listener) { _listener = listener; }



    @Override
    protected String doInBackground(String... params) {
        int retryCounter = 0;
        String output;
        while (retryCounter < 10) {
            try {
                output = dataReader();
                if (output != null)
                    return output;
            } catch (Exception e) {
                retryCounter++;
                MyThread.sleep(500);
            }
        }
        return null;
    }

    private String dataReader() throws IOException, URISyntaxException {
        StringBuilder response = new StringBuilder();
        URI uri = new URI("http://192.168.4.1:8080");
        URL url = uri.toURL();
        URLConnection urlC = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
        String read;
        while ((read = reader.readLine()) != null)
            response.append(read);
        return response.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            try {
                JSONObject obj = new JSONObject(response);
                int speed = obj.getInt("speed");
                int height = obj.getInt("altitude");
                long ms = obj.getLong("millis");
                _listener.onFinish(new SensorData(speed, height, ms));
            } catch (Exception e) {
                e.printStackTrace();
                _listener.onError();
            }
        } else {
            _listener.onError();
        }
    }
}
