package com.experience.essteling.a3.esstelingexperience.Helpers;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by Jaap-Jan on 12-6-2017.
 */

public class MyWifiThread implements IThread {

    IThread _success;
    IThread _fail;
    TextView _status;
    Activity _activity;

    Thread _thread;

    public MyWifiThread(IThread success, IThread fail, Activity act, TextView status) {
        _success = success;
        _fail = fail;
        _activity = act;
        _status = status;
    }

    public void start() {
        _thread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateStatusText("Connecting...");
                int tries = 0;
                WifiConnection.Connect(_activity);

                while (!WifiConnection.isConnected(_activity)) {
                    tries++;
                    MyMetingThread.sleep(1000);
                    if (tries == 10) {
                        updateStatusText("Connectie maken mislukt, probeer opnieuw...");
                        _fail.start();
                        return;
                    }
                }
                updateStatusText("Metingen aan het verzamelen...");
                _success.start();
            }
        });
        _thread.start();
    }

    private void updateStatusText(final String text) {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _status.setText(text);
            }
        });
    }
}
