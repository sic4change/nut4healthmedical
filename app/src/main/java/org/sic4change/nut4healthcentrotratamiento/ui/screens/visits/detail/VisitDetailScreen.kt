package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.CheckNUT4H
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitItemDetailScreen(
    visitState: VisitState, loading: Boolean = false,
    visitItem: Visit?, onEditClick: (Visit) -> Unit,
    onDeleteClick: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        if (visitItem != null) {
            VisitItemDetailScaffold(
                visitState = visitState,
                visitItem = visitItem,
                onClickEdit = onEditClick,
                onClickDelete = onDeleteClick
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    item {
                        Header(visitState = visitState)
                    }

                }
            }

        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(visitState: VisitState) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = SimpleDateFormat("dd/MM/yyyy").format(visitState.createdDate.value),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                    label = { Text(stringResource(R.string.creation_date), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = visitState.height.value.toString(),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Height, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
            label = { Text(stringResource(R.string.height), color = colorResource(R.color.disabled_color)) })

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = visitState.weight.value.toString(),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.SpaceBar, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
            label = { Text(stringResource(R.string.weight), color = colorResource(R.color.disabled_color)) })

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = visitState.armCircunference.value.toString(),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.MultipleStop, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
            label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })

        Spacer(modifier = Modifier.height(16.dp))

        if (visitState.status.value == stringResource(R.string.normopeso)) {
            TextField(value = visitState.status.value.capitalize(),
                onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorAccent),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.full_transparent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.colorAccent),  modifier = Modifier.clickable { })},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        } else if (visitState.status.value == stringResource(R.string.objetive_weight)) {
            TextField(value = visitState.status.value.capitalize(),
                onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.full_transparent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        } else if (visitState.status.value == stringResource(R.string.aguda_moderada)) {
            TextField(value = visitState.status.value.capitalize(),
                onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.orange),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.full_transparent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.orange),  modifier = Modifier.clickable { })},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        } else {
            TextField(value = visitState.status.value.capitalize(),
                onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.error),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.full_transparent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.error),  modifier = Modifier.clickable { })},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        }

        Spacer(modifier = Modifier.height(16.dp))

        DisableCheckNUT4H(text = stringResource(id = R.string.measlesVaccinated), visitState.measlesVaccinated.value)

        Spacer(modifier = Modifier.height(16.dp))

        DisableCheckNUT4H(text = stringResource(id = R.string.vitamineAVaccinated), visitState.vitamineAVaccinated.value)

        Spacer(modifier = Modifier.height(16.dp))

        val language = LocaleListCompat.getDefault()[0].toLanguageTag()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            elevation = 0.dp,
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.symtoms), color = colorResource(R.color.disabled_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),)
                visitState.symtoms.value.forEach {
                    var symtom = ""
                    if (language.contains("es-")) {
                        symtom = it.name
                    } else if(language.contains("en-")) {
                        symtom = it.name_en
                    } else {
                        symtom = it.name_fr
                    }
                    ListItem(
                        icon = { Icon(imageVector = Icons.Filled.LocalHospital, tint = colorResource(R.color.colorPrimary), contentDescription = null) },
                        text = { Text(text = symtom, color = colorResource(R.color.colorPrimary)) }
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
            elevation = 0.dp,
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.treatments), color = colorResource(R.color.disabled_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),)
                visitState.treatments.value.forEach {
                    var treatment = ""
                    if (language.contains("es-")) {
                        treatment = it.name
                    } else if(language.contains("en-")) {
                        treatment = it.name_en
                    } else {
                        treatment = it.name_fr
                    }
                    ListItem(
                        icon = { Icon(imageVector = Icons.Filled.LocalPharmacy, tint = colorResource(R.color.colorPrimary), contentDescription = null) },
                        text = { Text(text = treatment, color = colorResource(R.color.colorPrimary)) }
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = visitState.observations.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            maxLines = 5,
            singleLine = false,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
            label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun DisableCheckNUT4H(text: String, checked: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)
            .clickable(
                onClick = {

                }
            ),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            color = colorResource(R.color.colorPrimary),
            text = text,
            style = MaterialTheme.typography.body1,
        )

        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteVisit(showDialog: Boolean, setShowDialog: () -> Unit, visitId: String, caseId: String,
                       onDeleteVisit: (String) -> Unit, onDeleteVisitSelected: (String) -> Unit) {
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
                        onDeleteVisit(visitId)
                        onDeleteVisitSelected(caseId)
                    },
                ) {
                    Text(stringResource(R.string.accept), color = colorResource(R.color.white))
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                    },
                ) {
                    Text(stringResource(R.string.cancel),color = colorResource(R.color.white))
                }
            },
            text = {
                Text(stringResource(R.string.delete_visit_question))
            },
        )
    }

}


