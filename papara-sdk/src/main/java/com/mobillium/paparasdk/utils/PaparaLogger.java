package com.mobillium.paparasdk.utils;

import android.util.Log;

import com.mobillium.paparasdk.Papara;

/**
 * Created by oguzhandongul on 18/10/2016.
 */

public class PaparaLogger {
    private static String TAG = "PAPARA_SDK";
    private static String TAG_PARAMS = "PAPARA_SDK_PARAMS";

    public PaparaLogger() {
    }


    public static void writeErrorLog(String message) {
        if (Papara.getInstance().isDebugEnabled()) {
            Log.e(TAG, message);
        }
    }

    public static void writeInfoLog(String message) {
        if (Papara.getInstance().isDebugEnabled()) {
            Log.i(TAG, message);
        }
    }

    public static void writeResponseLog(String message) {
        if (Papara.getInstance().isDebugEnabled()) {
            Log.i(TAG_PARAMS, message);
        }
    }

    public static void writeDebugLog(String message) {
        if (Papara.getInstance().isDebugEnabled()) {
            Log.d(TAG, message);
        }
    }
}
