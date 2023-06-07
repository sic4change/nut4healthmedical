package org.sic4change.nut4healthcentrotratamiento.ui.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun CheckNUT4H(text: String, checked: Boolean, onCheckedChange : (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

        ) {
        Text(
            color = colorResource(R.color.colorPrimary),
            text = text,
            style = MaterialTheme.typography.body1,
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )
    }
}