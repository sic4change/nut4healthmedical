package org.sic4change.nut4healthcentrotratamiento

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import org.sic4change.nut4healthcentrotratamiento.ui.theme.NUT4HealthTheme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthApp

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        notificationSetting()
        setContent {
            NUT4HealthApp()
        }
    }

    private fun notificationSetting() {
        FirebaseMessaging.getInstance().subscribeToTopic("rosa_del_desierto")
        val childId: String? = intent.getStringExtra("childId")
        childId?.let {
            println("Ha llegado una notificación push del niño: ${childId}")
        }
    }
}









