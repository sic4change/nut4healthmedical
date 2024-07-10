package org.sic4change.nut4healthcentrotratamiento

import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.firebase.FirebaseApp
import org.sic4change.nut4healthcentrotratamiento.data.network.NetworkReceiver
import org.sic4change.nut4healthcentrotratamiento.data.network.NetworkCheckService
import org.sic4change.nut4healthcentrotratamiento.data.network.scheduleDailyCheck
import org.sic4change.nut4healthcentrotratamiento.ui.commons.StringResourcesUtil
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
class App: Application() {

    private lateinit var networkReceiver: NetworkReceiver

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate() {
        super.onCreate()

        StringResourcesUtil.initializeLocales(
            Locale("fr"),
            Locale("es"),
            Locale("ar"),
        )

        FirebaseApp.initializeApp(this)
        scheduleDailyCheck(this)

        // Initialize networkReceiver
        networkReceiver = NetworkReceiver()

        // Start network check service
        startNetworkCheckService()
    }

    private fun startNetworkCheckService() {
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

    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1
    }
}
