package com.papara.sdk.utils

import android.util.Log
import com.papara.sdk.Papara

object PaparaLogger {

    private const val TAG = "PAPARA_SDK"

    @JvmStatic
    fun writeErrorLog(message: String?) {
        if (Papara.getInstance().isDebugEnabled) {
            Log.e(TAG, message.orEmpty())
        }
    }

    @JvmStatic
    fun writeInfoLog(message: String?) {
        if (Papara.getInstance().isDebugEnabled) {
            Log.i(TAG, message.orEmpty())
        }
    }

    @JvmStatic
    fun writeResponseLog(message: String?) {
        if (Papara.getInstance().isDebugEnabled) {
            Log.i(TAG, message.orEmpty())
        }
    }

    @JvmStatic
    fun writeDebugLog(message: String?) {
        if (Papara.getInstance().isDebugEnabled) {
            Log.d(TAG, message.orEmpty())
        }
    }

    @JvmStatic
    fun writeSdkErrorLog() {
        if (Papara.getInstance().isDebugEnabled) {
            Log.e(TAG, "You must initialize the Papara SDK first")
        }
    }
}