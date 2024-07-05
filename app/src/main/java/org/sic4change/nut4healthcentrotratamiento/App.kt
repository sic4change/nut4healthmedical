package org.sic4change.nut4healthcentrotratamiento

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        // Check and request notification permission if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Assuming you have a MainActivity to handle the request
                ActivityCompat.requestPermissions(
                    MainActivity(), // Update this to your activity
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            } else {
                startNetworkCheckService()
            }
        } else {
            startNetworkCheckService()
        }
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
