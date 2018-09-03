package com.kounalakis.foursquare.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public enum NetworkStatus {OFFLINE, WIFI, MOBILE}

    private static NetworkStatus networkStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null) {
            return NetworkStatus.OFFLINE;
        }

        int type = activeNetwork.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return NetworkStatus.WIFI;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            return NetworkStatus.MOBILE;
        }

        return NetworkStatus.OFFLINE;
    }

    public static boolean isNetworkConnected(Context context) {
        return networkStatus(context) != NetworkStatus.OFFLINE;
    }
}