package org.sic4change.nut4healthcentrotratamiento

import android.app.Application
import android.os.Build
import android.content.Intent
import com.google.firebase.FirebaseApp
import org.sic4change.nut4healthcentrotratamiento.data.network.NetworkReceiver
import org.sic4change.nut4healthcentrotratamiento.data.network.NetworkCheckService
import org.sic4change.nut4healthcentrotratamiento.ui.commons.StringResourcesUtil
import java.util.Locale

class App: Application() {

    private val networkReceiver = NetworkReceiver()
        override fun onCreate() {
            super.onCreate()
            StringResourcesUtil.initializeLocales(
                Locale("fr"),
                Locale("es"),
                Locale("ar"),
            )
            Intent(this, NetworkCheckService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(it)
                } else {
                    startService(it)
                }
            }
            FirebaseApp.initializeApp(this)
        }
    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}