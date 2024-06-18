package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail


import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.STATUS
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitListItem
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CaseItemDetailScreen(
    caseState: CaseState, loading: Boolean = false, child: Child?, fefaItem: Tutor?,
    caseItem: Case?, visits: List<Visit>?, onEditClick: (Case) -> Unit,
    onCreateVisitClick: (Case) -> Unit, onItemClick: (Visit) -> Unit) {

    if (loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
        }
    } else {
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
                        CaseView(
                            caseItem = caseItem,
                            caseState = caseState,
                            child = child,
                            fefa = fefaItem,
                            visits = visits,
                            onItemClick = onItemClick, onItemMore = {}, onCreateVisitClick = onCreateVisitClick)
                    }
                }

            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
                }
            }

        }
    }



}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun CaseView(caseItem: Case, caseState: CaseState, child: Child?, fefa: Tutor?,
                     visits: List<Visit>?,
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
                            Icon(painterResource(R.mipmap.ic_brothers), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
            } else if (fefa != null) {
                TutorSummaryItem(
                    item = fefa!!,
                    expanded = caseState.expandedDetail.value,
                    onExpandDetail = { caseState.expandContractDetail() }
                )


                if (caseState.expandedDetail.value) {
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = fefa.phone,
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
                            Icon(Icons.Default.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.phone), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    val splitDate = SimpleDateFormat("dd/MM/yyyy").format(fefa.birthdate).split("/")
                    val yearsLabel = ChronoUnit.YEARS.between(
                        ZonedDateTime.parse(splitDate[2] + "-" +
                                splitDate[1] + "-" + splitDate[0] + "T00:00:00.000Z"), ZonedDateTime.now())
                    TextField(value = "${SimpleDateFormat("dd/MM/yyyy").format(fefa.birthdate)} ≈${yearsLabel} ${stringResource(R.string.years)}",
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
                            Icon(Icons.Default.Cake, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.birthdate), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = SimpleDateFormat("dd/MM/yyyy").format(fefa.createDate),
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
                            Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.creation_date), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = SimpleDateFormat("dd/MM/yyyy HH:mm").format(fefa.lastDate),
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
                            Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.last_date), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = fefa.ethnicity,
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
                            Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = fefa.sex,
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
                            if (fefa.sex == SEXS[0]) {
                                Icon(
                                    Icons.Filled.Female,
                                    null,
                                    tint = colorResource(R.color.colorPrimary),
                                    modifier = Modifier.clickable { /* .. */ })
                            } else if (fefa.sex == SEXS[1]) {
                                Icon(
                                    Icons.Filled.Male,
                                    null,
                                    tint = colorResource(R.color.colorPrimary),
                                    modifier = Modifier.clickable { /* .. */ })
                            } else {
                                Icon(
                                    painterResource(R.mipmap.ic_sex_selection),
                                    null,
                                    tint = colorResource(R.color.colorPrimary),
                                    modifier = Modifier.clickable { /* .. */ })
                            }
                        },
                        label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    AnimatedVisibility(visible = (fefa.sex== stringResource(R.string.male))) {
                        TextField(value = fefa.maleRelation,
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
                                Icon(painterResource(R.mipmap.ic_relation), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                            label = { Text(stringResource(R.string.relation), color = colorResource(R.color.disabled_color)) })
                    }
                    AnimatedVisibility(visible = (fefa.sex == stringResource(R.string.male))) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    AnimatedVisibility(visible = (fefa.sex == stringResource(R.string.female))) {
                        TextField(value = fefa.womanStatus,
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
                                Icon(Icons.Filled.PregnantWoman, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                            label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
                    }
                    AnimatedVisibility(visible = (fefa.sex== stringResource(R.string.female))) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    AnimatedVisibility(visible = (fefa.womanStatus == stringResource(R.string.pregnant) ||
                            fefa.womanStatus == stringResource(R.string.pregnant_and_infant))) {
                        TextField(value = fefa.weeks,
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
                                Icon(Icons.Filled.ViewWeek, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                            label = { Text(stringResource(R.string.weeks), color = colorResource(R.color.disabled_color)) })
                    }
                    AnimatedVisibility(visible = (fefa.womanStatus == stringResource(R.string.pregnant) ||
                            fefa.womanStatus == stringResource(R.string.pregnant_and_infant))) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    AnimatedVisibility(visible = (fefa.womanStatus == stringResource(R.string.pregnant))) {
                        TextField(value = fefa.childMinor,
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
                                Icon(Icons.Filled.ChildCare, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                            label = { Text(stringResource(R.string.child_minor_six_month), color = colorResource(R.color.disabled_color)) })
                    }
                    AnimatedVisibility(visible = (fefa.womanStatus == stringResource(R.string.pregnant))) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    AnimatedVisibility(visible = (fefa.womanStatus == stringResource(R.string.infant) ||
                            fefa.womanStatus == stringResource(R.string.pregnant_and_infant))) {
                        TextField(value = fefa.babyAge,
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
                                Icon(Icons.Filled.ChildFriendly, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                            label = { Text(stringResource(R.string.baby_age), color = colorResource(R.color.disabled_color)) })
                    }
                    AnimatedVisibility(visible = (fefa.womanStatus == stringResource(R.string.infant) ||
                            fefa.womanStatus  == stringResource(R.string.pregnant_and_infant))) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }


                    TextField(value = fefa.observations,
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
                            Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f, true)) {
                    Text(
                        text = caseItem.name,
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Start,
                        maxLines = 3
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        text = getAdmissionTypeServer(caseItem.admissionTypeServer),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        maxLines = 3
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        text = caseItem.status,
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        maxLines = 3
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        text = getClosedReason(caseItem.closedReason),
                        color = colorResource(R.color.disabled_color),
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Start,
                        maxLines = 3
                    )
                }

                Image(
                    modifier = Modifier.size(78.dp),
                    painter = if (caseItem.status == stringResource(R.string.close)) painterResource(id = R.mipmap.ic_case_closed) else painterResource(id = R.mipmap.ic_cases),
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
            if (STATUS.OPEN_STATUS_VALUES.contains(caseItem.status)) {
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

@Composable
fun getClosedReason(value: String) : String {
    if (value == "Referred") {
        return stringArrayResource(R.array.addmisionTypeOptions)[3]
    } else if (value == "Transfered"){
        return stringArrayResource(R.array.closedReasosnsOptions)[4]
    } else if (value == "Abandonment"){
        return stringArrayResource(R.array.closedReasosnsOptions)[0]
    } else if (value == "Recovered"){
        return stringArrayResource(R.array.closedReasosnsOptions)[1]
    } else if (value == "Unresponsive"){
        return stringArrayResource(R.array.closedReasosnsOptions)[2]
    } else if (value == "Death"){
        return stringArrayResource(R.array.closedReasosnsOptions)[6]
    } else {
        return "--"
    }
}

@Composable
fun getAdmissionTypeServer(value: String) : String {
    if (value == "Referred") {
        return stringArrayResource(R.array.addmisionTypeOptions)[3]
    } else if (value == "Transfered"){
        return stringArrayResource(R.array.addmisionTypeOptions)[4]
    } else if (value == "New Admission"){
        return stringArrayResource(R.array.addmisionTypeOptions)[0]
    } else if (value == "Readmission"){
        return stringArrayResource(R.array.addmisionTypeOptions)[1]
    } else if (value == "Relapse"){
        return stringArrayResource(R.array.addmisionTypeOptions)[2]
    } else {
        return "--"
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


