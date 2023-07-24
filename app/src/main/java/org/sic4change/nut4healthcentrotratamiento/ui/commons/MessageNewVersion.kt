package org.sic4change.nut4healthcentrotratamiento.ui.commons

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageNewVersion(context: Context) {
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(stringResource(R.string.new_version))
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    openAppInGooglePlay(context, "org.sic4change.nut4healthcentrotratamiento")
                    (context as? Activity)?.finishAffinity()
                }
            ) {
                Text(stringResource(R.string.update), color = colorResource(R.color.white))
            }
        },
    )

}

fun openAppInGooglePlay(context: Context, appId: String) {
    val appUri: Uri = Uri.parse("market://details?id=$appId")
    val playStoreIntent = Intent(Intent.ACTION_VIEW, appUri)
    if (playStoreIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(playStoreIntent)
    } else {
        val webUri: Uri = Uri.parse("https://play.google.com/store/apps/details?id=$appId")
        val webIntent = Intent(Intent.ACTION_VIEW, webUri)
        if (webIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(webIntent)
        }
    }
}