package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit

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
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildItemEditScreen(childState: ChildState, loading: Boolean = false,
                        onEditChild: (String, String, String, String, Date, String, String, String) -> Unit) {
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
                Header(childState = childState, onEditChild = onEditChild)
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
private fun Header(childState: ChildState,  onEditChild: (String, String, String, String, Date, String, String, String) -> Unit) {

    val sexs = listOf(
        stringResource(R.string.female), stringResource(R.string.Male))

    val etnicians = listOf(
        stringResource(R.string.pulaar), stringResource(R.string.wolof), stringResource(
            R.string.beydan), stringResource(R.string.haratin), stringResource(R.string.soninke),
        stringResource(R.string.autre))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.edit_child),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.name.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.name.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.name), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.surnames.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.surnames.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.surnames), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        DatePickerView(
            context = LocalContext.current,
            showMonths = true,
            value = SimpleDateFormat("dd/MM/yyyy").format(childState.birthday.value),
            setValue = { childState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)}
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = childState.expandedEtnician.value,
            onExpandedChange = {
                childState.expandedEtnician.value = !childState.expandedEtnician.value
            }
        ) {
            TextField(
                readOnly = true,
                value = childState.selectedOptionEtnician.value,
                onValueChange = { childState.selectedOptionEtnician.value = it
                    childState.etnician.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = childState.expandedEtnician.value
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
                    Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = childState.expandedEtnician.value,
                onDismissRequest = {
                    childState.expandedEtnician.value = false
                }
            ) {
                etnicians.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            childState.selectedOptionEtnician.value = selectionOption2
                            childState.expandedEtnician.value = false
                        }
                    ) {
                        Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = childState.expandedSex.value,
            onExpandedChange = {
                childState.expandedSex.value = !childState.expandedSex.value
            }
        ) {
            TextField(
                readOnly = true,
                value = childState.selectedOptionSex.value,
                onValueChange = { childState.selectedOptionSex.value = it
                    childState.sex.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = childState.expandedSex.value
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
                    Icon(Icons.Filled.EmojiPeople, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = childState.expandedSex.value,
                onDismissRequest = {
                    childState.expandedSex.value = false
                }
            ) {
                sexs.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            childState.selectedOptionSex.value = selectionOption2
                            childState.expandedSex.value = false
                        }
                    ) {
                        Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.observations.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.observations.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = (childState.name.value.isNotEmpty() &&
                childState.surnames.value.isNotEmpty())) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    onEditChild(childState.id.value, childState.tutorId.value, childState.name.value,
                        childState.surnames.value, childState.birthday.value,
                        childState.selectedOptionEtnician.value, childState.selectedOptionSex.value,
                        childState.observations.value)

                },
            ) {
                Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


