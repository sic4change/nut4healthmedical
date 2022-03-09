package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import DatePickerView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScreen(loading: Boolean = false, tutorItem: Tutor? ) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        if (tutorItem != null) {
            TutorItemDetailScaffold(
                tutorItem = tutorItem
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    item {
                        Header(tutorItem = tutorItem)
                    }
                    /*item.references.forEach {
                        val (icon, @StringRes stringRes) = it.type.createUiData()
                        section(icon, stringRes, it.references)
                    }*/
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(tutorItem: Tutor) {
    val options = listOf(stringResource(R.string.female), stringResource(R.string.Male), stringResource(
            R.string.Undefined),)
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    val options2 = listOf(stringResource(R.string.white), stringResource(R.string.black), stringResource(
        R.string.mestizo),)
    var expanded2 by remember { mutableStateOf(false) }
    var selectedOptionText2 by remember { mutableStateOf(options2[0]) }

    val options3 = listOf(stringResource(R.string.pregnant), stringResource(R.string.no_pregnant),)
    var expanded3 by remember { mutableStateOf(false) }
    var selectedOptionText3 by remember { mutableStateOf(options3[0]) }


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorItem.name, onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorItem.surnames, onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorItem.address, onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Place, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorItem.phone, onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        DatePickerView(
            context = LocalContext.current,
            value = SimpleDateFormat("dd/MM/yyyy").format(tutorItem.birthdate),
            setValue = { }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
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
                    Icon(Icons.Default.EmojiPeople, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = expanded2,
            onExpandedChange = {
                expanded2 = !expanded2
            }
        ) {
            TextField(
                readOnly = true,
                value = selectedOptionText2,
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded2
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
                    Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            )
            ExposedDropdownMenu(
                expanded = expanded2,
                onDismissRequest = {
                    expanded2 = false
                }
            ) {
                options2.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText2 = selectionOption2
                            expanded2 = false
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
            expanded = expanded3,
            onExpandedChange = {
                expanded3 = !expanded3
            }
        ) {
            TextField(
                readOnly = true,
                value = selectedOptionText3,
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded3
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
                    Icon(Icons.Filled.PregnantWoman, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            )
            ExposedDropdownMenu(
                expanded = expanded3,
                onDismissRequest = {
                    expanded3 = false
                }
            ) {
                options3.forEach { selectionOption3 ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText3 = selectionOption3
                            expanded2 = false
                        }
                    ) {
                        Text(text = selectionOption3, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorItem.observations, onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            maxLines = 5,
            singleLine = false,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}
/*

@ExperimentalMaterialApi
private fun LazyListScope.section(icon: ImageVector, @StringRes name: Int, items: List<Reference>) {
    if (items.isEmpty()) return

    item {
        Text(
            text = stringResource(name),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )
    }
    items(items) {
        ListItem(
            icon = { Icon(icon, contentDescription = null) },
            text = { Text(it.name) }
        )
    }
}

private fun ReferenceList.Type.createUiData(): Pair<ImageVector, Int> = when (this) {
    ReferenceList.Type.CHARACTER -> Icons.Default.Person to R.string.characters
    ReferenceList.Type.COMIC -> Icons.Default.Book to R.string.comics
    ReferenceList.Type.STORY -> Icons.Default.Bookmark to R.string.stories
    ReferenceList.Type.EVENT -> Icons.Default.Event to R.string.events
    ReferenceList.Type.SERIES -> Icons.Default.Collections to R.string.series
}*/
