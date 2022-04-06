package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail

import androidx.compose.animation.AnimatedVisibility
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
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildItemDetailScreen(
    childState: ChildState, loading: Boolean = false,
    childItem: Child?, onEditClick: (Child) -> Unit,
    onDeleteClick: (String) -> Unit,
    onCasesClick: (Child) -> Unit) {

    if (loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (childItem != null) {
            ChildItemDetailScaffold(
                childState = childState,
                childItem = childItem,
                onClickEdit = onEditClick,
                onCasesClick = onCasesClick,
                onClickDelete = onDeleteClick
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    item {
                        Header(childState = childState)
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
private fun Header(childState: ChildState) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.name.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            onValueChange = {}, readOnly = true,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
            label = { Text(stringResource(R.string.name), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.surnames.value,
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
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                    label = { Text(stringResource(R.string.surnames), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
        val splitDate = SimpleDateFormat("dd/MM/yyyy").format(childState.birthday.value).split("/")
        val yearsLabel = ChronoUnit.YEARS.between(
            ZonedDateTime.parse(splitDate[2] + "-" +
                    splitDate[1] + "-" + splitDate[0] + "T00:00:00.000Z"), ZonedDateTime.now())
        var monthsLabel = ChronoUnit.MONTHS.between(ZonedDateTime.parse(splitDate[2] + "-" +
                splitDate[1] + "-" + splitDate[0] + "T00:00:00.000Z"), ZonedDateTime.now())
        monthsLabel -= (yearsLabel * 12)
        TextField(value = "${SimpleDateFormat("dd/MM/yyyy").format(childState.birthday.value)} ≈${yearsLabel} ${stringResource(R.string.years)} ${monthsLabel} ${stringResource(R.string.months)}",
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.Cake, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                    label = { Text(stringResource(R.string.birthdate), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = SimpleDateFormat("dd/MM/yyyy").format(childState.createdDate.value),
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
        TextField(value = SimpleDateFormat("dd/MM/yyyy HH:mm").format(childState.lastDate.value),
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
                    label = { Text(stringResource(R.string.last_date), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.etnician.value,
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
                Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                    label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.sex.value,
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
                Icon(Icons.Default.EmojiPeople, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                    label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.observations.value,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteChild(showDialog: Boolean, setShowDialog: () -> Unit, childId: String, tutorId: String, onDeleteChild: (String) -> Unit, onDeleteChildSelected: (String) -> Unit) {
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
                        onDeleteChild(childId)
                        onDeleteChildSelected(tutorId)
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
                Text(stringResource(R.string.delete_child_question))
            },
        )
    }

}


