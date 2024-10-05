package com.android.androidbasics2024

import android.app.Application
import com.android.androidbasics2024.receiver.networkutils.NetworkManager.monitorNetwork

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        monitorNetwork(this)
    }
}