package com.example.hp.myapplication;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hp on 2.3.2018.
 */

public class mac {
    public String macadres;
    public mac(){}
    public mac(String macadres) {
        this.macadres = macadres;

    }
    public String getMacadres(){
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
    }


