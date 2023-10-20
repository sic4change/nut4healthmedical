package org.sic4change.nut4healthcentrotratamiento

import android.app.Application
import android.os.Build
import android.content.Intent
import org.sic4change.nut4healthcentrotratamiento.data.network.NetworkReceiver
import org.sic4change.nut4healthcentrotratamiento.data.network.NetworkCheckService

class App: Application() {

    private val networkReceiver = NetworkReceiver()
        override fun onCreate() {
            super.onCreate()
            Intent(this, NetworkCheckService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(it)
                } else {
                    startService(it)
                }
            }
        }
    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}