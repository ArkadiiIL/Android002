package com.arkadii.android002.presentation.network

import android.net.ConnectivityManager
import android.net.Network

class NetworkCallback(private val listener: OnNetworkChangeListener) :
    ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener.onNetworkChanged(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        listener.onNetworkChanged(false)
    }

    interface OnNetworkChangeListener {
        fun onNetworkChanged(isConnected: Boolean)
    }
}