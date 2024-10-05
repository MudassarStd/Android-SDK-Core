package com.android.androidbasics2024.receiver.networkutils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkManager {
    private var _networkState = MutableLiveData<Boolean>()
    val networkState : LiveData<Boolean> get() = _networkState

    fun monitorNetwork(context : Context?) {

        Log.d("TestingNetworkCallback", "Network monitor is setup")

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()


        val networkCallback = object: ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkState.postValue(true)
                Log.d("TestingNetworkCallback", "Network is available")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkState.postValue(false)
                Log.d("TestingNetworkCallback", "Network is lost")
            }
        }

        val connectivityManager = context?.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(request, networkCallback)
    }


}