package com.experience.essteling.a3.esstelingexperience.Helpers;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by jaapj on 8-6-2017.
 */

public class WifiConnection {

    private static final String WifiName = "MeetMijBabyOneMoreTime";
    private static final String WifiKey = "MeetMijPlease";

    private WifiConnection() {}

    public static void Connect(Context c) {
        Connect(c, true);
    }

    private static void Connect(Context c, boolean add) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = WifiName;
        config.preSharedKey = "\"" + WifiKey + "\"";

        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(config);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + WifiName + "\"")) {
                wifiManager.disconnect();
                if (add) {
                    wifiManager.enableNetwork(i.networkId, true);
                } else {
                    wifiManager.disableNetwork(i.networkId);
                }
                wifiManager.reconnect();
                break;
            }
        }
    }

    public static void Disconnect(Context c) {
        Connect(c, false);
    }

    public static boolean isConnected(Context c) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            String name = wifiInfo.getSSID();
            String ourName = "\"" + WifiName + "\"";

            return name.equals(ourName);
        }
        return false;
    }
}
