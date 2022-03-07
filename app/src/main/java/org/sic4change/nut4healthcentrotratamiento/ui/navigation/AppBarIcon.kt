package org.sic4change.nut4healthcentrotratamiento.ui.navigation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun AppBarIcon(imageVector: ImageVector, onClick: () -> Unit, contentDescription: String? = null) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = contentDescription, tint = colorResource(
            R.color.white))
    }
}