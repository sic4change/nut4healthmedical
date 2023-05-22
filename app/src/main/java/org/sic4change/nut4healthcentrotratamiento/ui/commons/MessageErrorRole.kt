package org.sic4change.nut4healthcentrotratamiento.ui.commons

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
fun MessageErrorRole(showDialog: Boolean, setShowDialog: () -> Unit, onLogout: () -> Unit, onLogoutSelected: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(stringResource(R.string.nut4health))
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                        onLogout()
                        onLogoutSelected()
                    },
                ) {
                    Text(stringResource(R.string.accept), color = colorResource(R.color.white))
                }
            },
            text = {
                Text(stringResource(R.string.credential_error))
            },
        )
    }

}