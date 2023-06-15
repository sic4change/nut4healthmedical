package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail


import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseListItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitListItem
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CaseItemDetailScreen(
    caseState: CaseState, loading: Boolean = false, child: Child?,
    caseItem: Case?, visits: List<Visit>?, onEditClick: (Case) -> Unit,
    onCreateVisitClick: (Case) -> Unit, onItemClick: (Visit) -> Unit) {

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

        if (caseItem != null) {
            CaseItemDetailScaffold(
                caseState = caseState,
                caseItem = caseItem,
                onClickEdit = onEditClick
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    CaseView(caseItem = caseItem, caseState = caseState, child = child, visits = visits,
                        onItemClick = onItemClick, onItemMore = {}, onCreateVisitClick = onCreateVisitClick)
                }
            }

        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun CaseView(caseItem: Case, caseState: CaseState, child: Child?, visits: List<Visit>?,
                     onItemClick: (Visit) -> Unit, onItemMore: (Visit) -> Unit,
                     onCreateVisitClick: (Case) -> Unit) {

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {

        item {
            Spacer(modifier = Modifier.height(16.dp))
            if (child != null) {
                ChildSummaryItem(
                    item = child,
                    expanded = caseState.expandedDetail.value,
                    onExpandDetail = { caseState.expandContractDetail() }
                )

                if (caseState.expandedDetail.value) {
                    TextField(value = child.brothers.toString(),
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
                            Icon(Icons.Filled.Numbers, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                        label = { Text(stringResource(R.string.child_brothers), color = colorResource(R.color.disabled_color)) })

                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = SimpleDateFormat("dd/MM/yyyy").format(child.createDate),
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
                    TextField(value = SimpleDateFormat("dd/MM/yyyy HH:mm").format(child.lastDate),
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
                    TextField(value = child.ethnicity,
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
                    TextField(value = child.sex,
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
                            if (child.sex == SEXS[0]) Icon(Icons.Filled.Female, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})
                            else Icon(Icons.Filled.Male, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = child.observations,
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
            Row( modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = caseItem.name,
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        maxLines = 3
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        text = caseItem.status,
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        maxLines = 3
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        text = caseItem.observations,
                        color = colorResource(R.color.disabled_color),
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        maxLines = 3
                    )
                }

                Image(
                    modifier = Modifier.size(78.dp).weight(1f),
                    painter = painterResource(id = R.mipmap.ic_cases),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (visits != null && visits.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_visits),
                    color = colorResource(R.color.colorPrimary),
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            } else if (visits != null && visits.isNotEmpty()){
                Text(
                    text = stringResource(R.string.visits_title),
                    color = colorResource(R.color.colorPrimary),
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (visits == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
                }
            }

        }

        if (visits != null){
            items(visits) {
                VisitListItem(
                    item = it,
                    modifier = Modifier.clickable { onItemClick(it) },
                    onItemMore = onItemMore
                )
            }
        }

        item {
            if (caseItem.status == stringResource(R.string.open)) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                        onClick = { onCreateVisitClick(caseItem) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            stringResource(R.string.create_visit),
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.h5,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
        
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteCase(showDialog: Boolean, setShowDialog: () -> Unit, caseId: String, childId: String, onDeleteCase: (String) -> Unit, onDeleteCaseSelected: (String) -> Unit) {
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
                        onDeleteCase(caseId)
                        onDeleteCaseSelected(childId)
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
                Text(stringResource(R.string.delete_case_question))
            },
        )
    }

}


