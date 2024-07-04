package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.ui.commons.StringResourcesUtil.Companion.doesStringMatchAnyLocale
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NextItemBottomPreview(item: Cuadrant?) {
    if (item != null) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(value = "${item.name}".toString().capitalize(), onValueChange = {}, readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(R.color.colorPrimary),
                        backgroundColor = colorResource(R.color.white),
                        cursorColor = colorResource(R.color.full_transparent),
                        disabledLabelColor =  colorResource(R.color.full_transparent),
                        focusedIndicatorColor = colorResource(R.color.full_transparent),
                        unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                    ),
                    textStyle = MaterialTheme.typography.h5,
                    leadingIcon = {
                        Icon(Icons.Filled.ChildCare, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
                if (doesStringMatchAnyLocale(LocalContext.current, "open", item.status)) {
                    TextField(value = "${item.status}".toString().capitalize(), onValueChange = {}, readOnly = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorAccent),
                            backgroundColor = colorResource(R.color.white),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor =  colorResource(R.color.full_transparent),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        textStyle = MaterialTheme.typography.h5,
                        leadingIcon = {
                            Icon(Icons.Filled.Folder, null, tint = colorResource(R.color.colorAccent),  modifier = Modifier.clickable { /* .. */})})

                } else {
                    TextField(value = "${item.status}".toString().capitalize(), onValueChange = {}, readOnly = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.white),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor =  colorResource(R.color.full_transparent),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        textStyle = MaterialTheme.typography.h5,
                        leadingIcon = {
                            Icon(Icons.Filled.Folder, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})

                }

                TextField(value = "${stringResource(R.string.visits)} ${item.visits} ", onValueChange = {}, readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(R.color.colorPrimary),
                        backgroundColor = colorResource(R.color.white),
                        cursorColor = colorResource(R.color.full_transparent),
                        disabledLabelColor =  colorResource(R.color.full_transparent),
                        focusedIndicatorColor = colorResource(R.color.full_transparent),
                        unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                    ),
                    textStyle = MaterialTheme.typography.h5,
                    leadingIcon = {
                        Icon(Icons.Filled.EditCalendar, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
                TextField(value = "${stringResource(R.string.last_visits)} ${SimpleDateFormat("dd/MM/yyyy").format(item.lastdate)}", onValueChange = {}, readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(R.color.colorPrimary),
                        backgroundColor = colorResource(R.color.white),
                        cursorColor = colorResource(R.color.full_transparent),
                        disabledLabelColor =  colorResource(R.color.full_transparent),
                        focusedIndicatorColor = colorResource(R.color.full_transparent),
                        unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                    ),
                    textStyle = MaterialTheme.typography.h5,
                    leadingIcon = {
                        Icon(Icons.Filled.EditCalendar, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
                Button(
                    onClick = {  },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary))
                ) {
                    Text(color = colorResource(R.color.white), text = stringResource(id = R.string.go_to_detail))
                }
            }
        }
    } else {
        Spacer(modifier = Modifier.height(1.dp))
    }

}

