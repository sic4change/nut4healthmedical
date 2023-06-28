package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit

import DatePickerView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable



fun CaseItemEditScreen(caseState: CaseState, loading: Boolean = false,
                       onEditCase: (String, String, String, String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Header(caseState = caseState, onEditCase = onEditCase)
            }
            /*item.references.forEach {
                val (icon, @StringRes stringRes) = it.type.createUiData()
                section(icon, stringRes, it.references)
            }*/
        }

    }

}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(caseState: CaseState,  onEditCase: (String, String, String, String) -> Unit) {

    val status = listOf(
        stringResource(R.string.open), stringResource(R.string.close))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.edit_case),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = caseState.name.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {caseState.name.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.name), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = caseState.expandedStatus.value,
            onExpandedChange = {
                caseState.expandedStatus.value = !caseState.expandedStatus.value
            }
        ) {
            TextField(
                readOnly = true,
                value = caseState.selectedOptionStatus.value,
                onValueChange = { caseState.selectedOptionStatus.value = it
                    caseState.status.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = caseState.expandedStatus.value
                    )
                },
                textStyle = MaterialTheme.typography.h5,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Filled.Folder, null, tint = colorResource(R.color.colorPrimary),  )},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = caseState.expandedStatus.value,
                onDismissRequest = {
                    caseState.expandedStatus.value = false
                }
            ) {
                status.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            caseState.selectedOptionStatus.value = selectionOption2
                            caseState.expandedStatus.value = false
                        }
                    ) {
                        Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = caseState.observations.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {caseState.observations.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = (caseState.name.value.isNotEmpty())) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    onEditCase(caseState.id.value,caseState.name.value,
                        caseState.selectedOptionStatus.value, caseState.observations.value)

                },
            ) {
                Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


