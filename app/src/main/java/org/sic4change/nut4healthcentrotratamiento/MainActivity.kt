package org.sic4change.nut4healthcentrotratamiento

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
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
        FirebaseDataSource.configFirestore()
        setContent {
            NUT4HealthApp()
        }
    }

    private fun notificationSetting() {
        val childId: String? = intent.getStringExtra("childId")
        childId?.let {
            notificationChildId = childId
        }
    }
}









