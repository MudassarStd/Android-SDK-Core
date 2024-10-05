package com.android.androidbasics2024

import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.androidbasics2024.receiver.AirPlaneModeReceiver
import com.android.androidbasics2024.receiver.InternetConnectionReceiver
import com.android.androidbasics2024.receiver.OnAirplaneModeChanged
import com.android.androidbasics2024.receiver.networkutils.NetworkManager.monitorNetwork
import com.android.androidbasics2024.receiver.networkutils.NetworkManager.networkState
import com.android.androidbasics2024.ui.theme.AndroidBasics2024Theme

class MainActivity : ComponentActivity() , OnAirplaneModeChanged{

    val internetBroadcastReceiver = InternetConnectionReceiver()
    val airPlaneModeReceiver = AirPlaneModeReceiver(this)
    val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBasics2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(internetBroadcastReceiver,intent)
        registerReceiver(airPlaneModeReceiver, IntentFilter(ACTION_AIRPLANE_MODE_CHANGED))
        Log.d("BroadcastTestingGo", "RegisteredBroadcast")
        setupAirplaneModeObserver()


        setupNetworkCallback()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(internetBroadcastReceiver)
        registerReceiver(airPlaneModeReceiver, IntentFilter(ACTION_AIRPLANE_MODE_CHANGED))
        Log.d("BroadcastTestingGo", "UnRegisteredBroadcast")
    }

    override fun isAirplaneModeOn(isOn: Boolean) {
        Log.d("BroadcastTestingGo", "Callback triggered")
    }

    fun setupAirplaneModeObserver() {
        airPlaneModeReceiver.airplaneModeListener.observe(this) {
            Log.d("BroadcastTestingGo", "Observer setup called with airplane mode $it")
        }
    }

    fun setupNetworkCallback() {
       networkState.observe(this) {
           val networkState = if (it) "Available" else "Lost"
           Log.d("TestingNetworkCallback", "Network state now $networkState")
       }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Button(onClick = {  }) {
        Text(text = "Button")
    }

}
