package com.papara.sdk.sampleapp

import android.app.Application
import com.papara.sdk.Papara
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Papara.getInstance().isDebugEnabled = true
    }
}
