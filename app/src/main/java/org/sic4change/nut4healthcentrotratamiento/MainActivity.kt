package org.sic4change.nut4healthcentrotratamiento

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.firebase.messaging.FirebaseMessaging
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthApp

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    companion object{
        var notificationChildId = ""
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        notificationChildId = ""
        notificationSetting()
        setContent {
            NUT4HealthApp()
        }
    }

    private fun notificationSetting() {
        FirebaseMessaging.getInstance().subscribeToTopic("rosa_del_desierto")
        val childId: String? = intent.getStringExtra("childId")
        childId?.let {
            notificationChildId = childId
        }
    }
}









