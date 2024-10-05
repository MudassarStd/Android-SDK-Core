package com.android.androidbasics2024.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData

class AirPlaneModeReceiver(private val interfaceListener : OnAirplaneModeChanged) : BroadcastReceiver() {

    var airplaneModeListener = MutableLiveData(false)

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneON = intent?.getBooleanExtra("state", false)
        if (isAirplaneON == true) {
//            airplaneModeListener.value = true   --  setting live data value
            Log.d("BroadcastTestingGo", "onReceived: AirplaneMode is ON")
            interfaceListener.isAirplaneModeOn(true)
        } else {
//            airplaneModeListener.value = false  -- live data here as well
            Log.d("BroadcastTestingGo", "onReceived: AirplaneMode is OFF")
            interfaceListener.isAirplaneModeOn(false)
        }
    }
}