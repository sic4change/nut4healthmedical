package org.sic4change.nut4healthcentrotratamiento.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter

object NetworkUtils {

    interface NetworkChangeListener {
        fun onNetworkChange(isConnected: Boolean)
    }

    private var networkChangeListener: NetworkChangeListener? = null

    fun registerNetworkChangeListener(context: Context, listener: NetworkChangeListener) {
        networkChangeListener = listener
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, filter)
    }

    fun unregisterNetworkChangeListener(context: Context) {
        networkChangeListener = null
        context.unregisterReceiver(networkChangeReceiver)
    }

    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            networkChangeListener?.onNetworkChange(isInternetAvailable(context!!))
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        return true
                    }
                } catch (e: Exception) {
                    return false
                }
            }
        }
        return false
    }
}

