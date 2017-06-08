package com.experience.essteling.a3.esstelingexperience;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class DataHandler extends AsyncTask<String, Void, String> {

    private IDataListener _listener;
    private SensorDataList _list;

    public DataHandler(SensorDataList list, IDataListener listener) { _listener = listener; _list = list; }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL("http://192.168.4.1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String read;
            while ((read = reader.readLine()) != null)
                response.append(read);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response.toString();
    }

    protected void onPostExecute(String response) {
        if (response != null) {
            try {
                JSONObject obj = new JSONObject(response);
                int speed = obj.getInt("snelheid");
                int height = obj.getInt("altitude");
                long ms = obj.getLong("millis");

                _list.add(new SensorData(speed, height, ms));
                _listener.onFinish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            _listener.onError();
        }
    }
}
