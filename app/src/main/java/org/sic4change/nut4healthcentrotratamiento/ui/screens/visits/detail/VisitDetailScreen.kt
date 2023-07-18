package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.CurrenStatusView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitItemDetailScreen(
    visitState: VisitState, loading: Boolean = false, child: Child?, fefa: Tutor?,
    visitItem: Visit?, onEditClick: (Visit) -> Unit,
    onDeleteClick: (String) -> Unit) {

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
        if (visitItem != null) {
            VisitItemDetailScaffold(
                visitState = visitState,
                visitItem = visitItem,
                onClickEdit = onEditClick,
                onClickDelete = onDeleteClick
            ) { padding ->
                Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    VisitView(visitState = visitState, loading = loading, child = child, fefa = fefa)
                }
            }

        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun VisitView(loading: Boolean, visitState: VisitState, child: Child?, fefa: Tutor?) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateString = simpleDateFormat.format(visitState.childDateMillis.value)

    val monthsBetween = ChronoUnit.MONTHS.between(
        YearMonth.from(LocalDate.parse(dateString)), YearMonth.from(LocalDate.now())
    )
    if (!loading) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
            item {
                if (child != null) {
                    ChildSummaryItem(
                        item = child,
                        expanded = visitState.expandedDetail.value,
                        onExpandDetail = { visitState.expandContractDetail() }
                    )
                    if (visitState.expandedDetail.value) {
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
                        item = fefa,
                        expanded = visitState.expandedDetail.value,
                        onExpandDetail = { visitState.expandContractDetail() }
                    )
                    if (visitState.expandedDetail.value) {
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
            }

           item {
                Spacer(modifier = Modifier.height(16.dp))
               val color = if (visitState.status.value == stringResource(R.string.aguda_severa)) {
                   colorResource(R.color.error)
               } else if (visitState.status.value == stringResource(R.string.aguda_moderada)){
                   colorResource(R.color.orange)
               } else if (visitState.status.value == stringResource(R.string.normopeso)){
                   colorResource(R.color.colorAccent)
               } else {
                   colorResource(R.color.colorPrimary)
               }
               Card(
                   shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                   backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                   modifier = Modifier.fillMaxSize().padding(16.dp, 16.dp, 16.dp, 0.dp)
               ) {
                   if (fefa != null) {
                       Column {
                           VisitTitle(visitState.visitNumber.value, color)
                           Spacer(modifier = Modifier.height(16.dp))
                           if (visitState.visitNumber.value == 1) {
                               AddmisionTypeView(visitState.admissionType.value)
                               Spacer(modifier = Modifier.height(16.dp))
                           }
                           SteptTitle(stringResource(R.string.step1_title))
                           Spacer(modifier = Modifier.height(16.dp))
                           ItemViewIcon(visitState.armCircunference.value.toString(),
                               stringResource(R.string.arm_circunference), Icons.Filled.MultipleStop)

                           Spacer(modifier = Modifier.height(16.dp))

                           CurrenStatusView(visitState)

                           Spacer(modifier = Modifier.height(16.dp))

                           ItemViewIcon(visitState.observations.value, stringResource(R.string.observations), Icons.Filled.Edit)

                           Spacer(modifier = Modifier.height(16.dp))
                           AnimatedVisibility(visitState.status.value.isNotEmpty()
                                   && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                   || visitState.status.value == stringResource(R.string.aguda_severa))){
                               Column {
                                   Spacer(modifier = Modifier.height(16.dp))
                                   Divider(color = colorResource(R.color.disabled_color), thickness = 1.dp)
                                   Spacer(modifier = Modifier.height(16.dp))
                                   SteptTitle(stringResource(R.string.step3_title))
                               }

                           }

                           Spacer(modifier = Modifier.height(16.dp))

                           AnimatedVisibility(visitState.status.value.isNotEmpty()
                                   && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                   || visitState.status.value == stringResource(R.string.aguda_severa))
                                   && (visitState.womanStatus.value == stringResource(R.string.pregnant)
                                   || visitState.womanStatus.value == stringResource(R.string.pregnant_and_infant))
                                   && visitState.pregnantWeeks.value >= 24
                                   && visitState.visitNumber.value == 1) {
                               Column {
                                   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Icon(painterResource(R.mipmap.ic_capsules), null, tint = colorResource(R.color.colorPrimary))
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(stringResource(R.string.albendazole_a_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                   }

                                       Column(
                                           horizontalAlignment = Alignment.CenterHorizontally,
                                           verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                           modifier = Modifier
                                               .wrapContentSize()
                                               .padding(16.dp, 0.dp)
                                       ) {
                                           Column(
                                               verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                               modifier = Modifier
                                                   .wrapContentSize()
                                                   .padding(0.dp, 0.dp)
                                           ) {
                                               Text(stringResource(R.string.vitamine_dosis),
                                                   color = colorResource(R.color.disabled_color)
                                               )
                                               Text("${stringResource(R.string.abendazol_400_full)} o ${stringResource(R.string.mebendazol_400_full)}",
                                                   color = colorResource(R.color.black_gray)
                                               )
                                               
                                           }
                                       }

                                   Spacer(modifier = Modifier.height(16.dp))
                               }
                           }

                           AnimatedVisibility(visitState.status.value.isNotEmpty()
                                   && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                   || visitState.status.value == stringResource(R.string.aguda_severa))
                                   && (visitState.womanStatus.value == stringResource(R.string.pregnant)
                                   || visitState.womanStatus.value == stringResource(R.string.infant)
                                   || visitState.womanStatus.value == stringResource(R.string.pregnant_and_infant))){
                               Column {
                                   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Icon(painterResource(R.mipmap.ic_vitamine), null, tint = colorResource(R.color.colorPrimary))
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(stringResource(R.string.ferro_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                   }

                                       Column {
                                           Column(modifier = Modifier
                                               .wrapContentSize()
                                               .padding(16.dp, 0.dp)) {
                                               Text(stringResource(R.string.vitamine_dosis), color = colorResource(R.color.disabled_color))
                                               Text(stringResource(R.string.ferro_admin), color = colorResource(R.color.black_gray))
                                           }
                                           AnimatedVisibility(visitState.status.value.isNotEmpty()
                                                   && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                                   || visitState.status.value == stringResource(R.string.aguda_severa))
                                                   && (visitState.womanStatus.value == stringResource(R.string.pregnant)
                                                   || visitState.womanStatus.value == stringResource(R.string.pregnant_and_infant))){
                                               Column {
                                                   Spacer(modifier = Modifier.height(8.dp))
                                                   ItemViewIcon(visitState.selectedCapsulesFerro.value, stringResource(R.string.capsules_hierro_folico_checked), Icons.Filled.Medication)
                                               }

                                           }
                                       }
                               }

                           }

                           AnimatedVisibility(visitState.status.value.isNotEmpty()
                                   && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                   || visitState.status.value == stringResource(R.string.aguda_severa))
                                   && (visitState.womanStatus.value == stringResource(R.string.infant)
                                   || visitState.womanStatus.value == stringResource(R.string.pregnant_and_infant))
                                   && (visitState.womanChildWeeks.value <= 6)){

                               Column {
                                   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Icon(painterResource(R.mipmap.ic_vitamine), null, tint = colorResource(R.color.colorPrimary))
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(stringResource(R.string.vitamine_a_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                   }
                                       Column(
                                           verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                           modifier = Modifier
                                               .wrapContentSize()
                                               .padding(16.dp, 0.dp)
                                       ) {
                                           Column(
                                               verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                               modifier = Modifier
                                                   .wrapContentSize()
                                                   .padding(0.dp, 0.dp)
                                           ) {
                                               Text(stringResource(R.string.vitamine_dosis),
                                                   color = colorResource(R.color.disabled_color)
                                               )
                                               Text(stringResource(R.string.vitamine_red),
                                                   color = colorResource(R.color.black_gray)
                                               )
                                           }
                                       }

                                   Spacer(modifier = Modifier.height(16.dp))
                                   AnimatedVisibility(visitState.status.value.isNotEmpty()
                                           && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                           || visitState.status.value == stringResource(R.string.aguda_severa))){
                                       Column {
                                           Spacer(modifier = Modifier.height(16.dp))
                                           Divider(color = colorResource(R.color.disabled_color), thickness = 1.dp)
                                           Spacer(modifier = Modifier.height(16.dp))
                                           SteptTitle(stringResource(R.string.step4_title))
                                       }
                                   }

                                   Spacer(modifier = Modifier.height(16.dp))

                                   Column(
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(0.dp, 16.dp)
                                   ) {
                                       Text(
                                           text = stringResource(R.string.fefa_ration),
                                           color = colorResource(R.color.disabled_color),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.h6,
                                           fontWeight = FontWeight.Bold)
                                       Spacer(modifier = Modifier.height(8.dp))
                                       Text(
                                           text = stringResource(R.string.fefa_ration_pam),
                                           color = colorResource(R.color.black_gray),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.body1,
                                           fontWeight = FontWeight.Bold)
                                       Spacer(modifier = Modifier.height(4.dp))
                                       Text(
                                           text = stringResource(R.string.fefa_ration_csa),
                                           color = colorResource(R.color.black_gray),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.body1,
                                           fontWeight = FontWeight.Bold)
                                   }
                               }


                           }
                       }
                   } else if (child != null) {
                       if (visitState.point.value.type == "Otro") {
                           Column {
                               Text(
                                   text = "${stringResource(R.string.visit)} ${visitState.visitNumber.value}",
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .background(color = color)
                                       .align(Alignment.CenterHorizontally)
                                       .padding(0.dp, 16.dp),
                                   color = colorResource(R.color.white),
                                   style = MaterialTheme.typography.h5,
                                   textAlign = TextAlign.Center
                               )
                               Spacer(modifier = Modifier.height(16.dp))
                               if (visitState.visitNumber.value == 1) {
                                   AddmisionTypeView(visitState.admissionType.value)
                                   Spacer(modifier = Modifier.height(16.dp))
                               }
                               SteptTitle(stringResource(R.string.step1_title))
                               Spacer(modifier = Modifier.height(16.dp))
                               ItemViewIcon(visitState.height.value, stringResource(R.string.height), Icons.Filled.Height)
                               Spacer(modifier = Modifier.height(16.dp))
                               ItemViewImage(visitState.weight.value, stringResource(R.string.weight), R.mipmap.ic_weight)
                               Spacer(modifier = Modifier.height(16.dp))
                               AnimatedVisibility(visible = ((visitState.armCircunference.value != 30.0) )) {
                                   ItemViewIcon(visitState.armCircunference.value.toString(), stringResource(R.string.arm_circunference), Icons.Filled.MultipleStop)
                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty() && (monthsBetween >= 6 && monthsBetween <= 60))) {
                                   Spacer(modifier = Modifier.height(16.dp))
                               }
                               ItemViewImage(visitState.selectedEdema.value, stringResource(R.string.edema), R.mipmap.ic_edema)

                               Spacer(modifier = Modifier.height(16.dp))
                               Card(modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(16.dp, 0.dp),
                                   elevation = 0.dp,
                                   backgroundColor = colorResource(R.color.white))
                               {
                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .wrapContentSize()
                                           .padding(16.dp)
                                   ) {
                                       Text(text = stringResource(R.string.complications), color = colorResource(R.color.disabled_color),
                                           modifier = Modifier
                                               .fillMaxWidth()
                                               .padding(16.dp, 0.dp))

                                       if (visitState.complications.value.isEmpty()) {
                                           Text(text = stringResource(R.string.no_complications), color = colorResource(R.color.disabled_color),
                                               style = MaterialTheme.typography.caption,
                                               modifier = Modifier
                                                   .fillMaxWidth()
                                                   .padding(16.dp, 0.dp))
                                       } else {
                                           visitState.complications.value.forEach { complication ->
                                               ItemListComplicationsDisabled(
                                                   complication = complication,
                                                   checked = complication.selected,
                                                   onCheckedChange = {
                                                       val complicationsToUpdate: MutableList<Complication> = mutableListOf()
                                                       visitState.complications.value.forEach { item ->
                                                           if (item.id == complication.id) {
                                                               item.selected = it
                                                           }
                                                           complicationsToUpdate.add(item)
                                                       }
                                                       visitState.complications.value = mutableListOf()
                                                       visitState.complications.value.addAll(complicationsToUpdate)
                                                   }
                                               )
                                           }
                                       }
                                   }
                               }

                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty())) {
                                   CurrenStatusView(visitState = visitState)
                               }

                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
                                   Column{
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step2_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }
                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
                                   Column {
                                       ItemViewImage(visitState.selectedVomitos.value, stringResource(R.string.vomits), R.mipmap.ic_vomit)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       ItemViewImage(visitState.selectedDiarrea.value, stringResource(R.string.diarrea), R.mipmap.ic_diarrea)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       ItemViewImage(visitState.selectedFiebre.value, stringResource(R.string.fiebre), R.mipmap.ic_fiebre)
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       && visitState.visitNumber.value == 1) {
                                   Column {
                                       Spacer(modifier = Modifier.height(16.dp))
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step4_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }

                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       && visitState.visitNumber.value == 1) {
                                   Column {
                                       Column(
                                           modifier = Modifier
                                               .wrapContentSize()
                                               .padding(16.dp, 16.dp)
                                       ) {
                                           Text(stringResource(R.string.amoxicilina), color = colorResource(R.color.disabled_color))
                                           if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 5.0)) {
                                               Text(stringResource(R.string.amoxicilina_125), color = colorResource(R.color.black_gray))
                                           } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 10.0)) {
                                               Text(stringResource(R.string.amoxicilina_250), color = colorResource(R.color.black_gray))
                                           } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 20.0)) {
                                               Text(stringResource(R.string.amoxicilina_500), color = colorResource(R.color.black_gray))
                                           } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 20.0 && visitState.weight.value.toDouble() < 35.0)) {
                                               Text(stringResource(R.string.amoxicilina_750), color = colorResource(R.color.black_gray))
                                           } else {
                                               Text(stringResource(R.string.amoxicilina_1000), color = colorResource(R.color.black_gray))
                                           }
                                       }

                                       Spacer(modifier = Modifier.height(16.dp))
                                       ItemViewIcon(visitState.selectedAmoxicilina.value, stringResource(R.string.another_tratements), Icons.Filled.Medication)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       ItemViewIcon(visitState.othersTratments.value, stringResource(R.string.another_tratements), Icons.Filled.Medication)

                                   }
                               }

                               Spacer(modifier = Modifier.height(16.dp))

                               ItemViewIcon(visitState.observations.value, stringResource(R.string.observations), Icons.Filled.Edit)


                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       )) {
                                   Column{
                                       Spacer(modifier = Modifier.height(16.dp))
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step4_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }

                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       )) {

                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(0.dp, 16.dp)
                                   ) {
                                       Text(
                                           text = stringResource(R.string.plumpy_one),
                                           color = colorResource(R.color.black),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.h5,
                                           fontWeight = FontWeight.Bold)

                                       Row(modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                                           horizontalArrangement = Arrangement.SpaceBetween,
                                           verticalAlignment = Alignment.CenterVertically) {
                                           Image(
                                               modifier = Modifier.size(78.dp).weight(1f),
                                               painter = painterResource(id = R.mipmap.ic_plumpy),
                                               contentDescription = null,
                                           )

                                           Text(
                                               text = stringResource(R.string.plumpy_fiveteeen),
                                               color = colorResource(R.color.colorPrimary),
                                               textAlign = TextAlign.Center,
                                               style = MaterialTheme.typography.h5,
                                               fontWeight = FontWeight.Bold)
                                       }

                                   }

                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       )) {
                                   Column{
                                       Spacer(modifier = Modifier.height(16.dp))
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step4_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }

                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       )) {

                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(0.dp, 16.dp)
                                   ) {
                                       Text(
                                           text = stringResource(R.string.plumpy_two),
                                           color = colorResource(R.color.black),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.h5,
                                           fontWeight = FontWeight.Bold)

                                       Row(modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                                           horizontalArrangement = Arrangement.SpaceBetween,
                                           verticalAlignment = Alignment.CenterVertically) {
                                           Image(
                                               modifier = Modifier.size(78.dp).weight(1f),
                                               painter = painterResource(id = R.mipmap.ic_plumpy),
                                               contentDescription = null,
                                           )

                                           if (visitState.weight.value.toDouble() >= 3.0 && visitState.weight.value.toDouble() < 3.5) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_8),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 3.5 && visitState.weight.value.toDouble() < 5.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_10),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 7.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_15),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 7.0 && visitState.weight.value.toDouble() < 10.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_20),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 15.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_30),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 15.0 && visitState.weight.value.toDouble() < 20.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_35),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 20.0 && visitState.height.value.toDouble() < 30.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_40),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 30.0 && visitState.weight.value.toDouble() < 40.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_50),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 40.0 && visitState.weight.value.toDouble() <= 60.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_55),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           }

                                       }


                                   }

                               }

                               Spacer(modifier = Modifier.height(16.dp))
                           }
                       } else {
                           Column {
                               VisitTitle(visitState.visitNumber.value, color)
                               Spacer(modifier = Modifier.height(16.dp))
                               if (visitState.visitNumber.value == 1) {
                                   AddmisionTypeView(visitState.admissionType.value)
                                   Spacer(modifier = Modifier.height(16.dp))
                               }
                               SteptTitle(stringResource(R.string.step1_title))
                               Spacer(modifier = Modifier.height(16.dp))
                               ItemViewIcon(visitState.height.value, stringResource(R.string.height), Icons.Filled.Height)
                               Spacer(modifier = Modifier.height(16.dp))
                               ItemViewImage(visitState.weight.value, stringResource(R.string.weight), R.mipmap.ic_weight)
                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = ((visitState.armCircunference.value != 30.0) )) {
                                   ItemViewIcon(visitState.armCircunference.value.toString(), stringResource(R.string.arm_circunference), Icons.Filled.MultipleStop)
                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty() && (monthsBetween >= 6 && monthsBetween <= 60))) {
                                   Spacer(modifier = Modifier.height(16.dp))
                               }

                               ItemViewImage(visitState.selectedEdema.value, stringResource(R.string.edema), R.mipmap.ic_edema)

                               Spacer(modifier = Modifier.height(16.dp))

                               Card(modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(16.dp, 0.dp),
                                   elevation = 0.dp,
                                   backgroundColor = colorResource(R.color.white))
                               {
                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .wrapContentSize()
                                           .padding(16.dp)
                                   ) {
                                       Text(text = stringResource(R.string.complications), color = colorResource(R.color.disabled_color),
                                           modifier = Modifier
                                               .fillMaxWidth()
                                               .padding(16.dp, 0.dp))

                                       if (visitState.complications.value.isEmpty()) {
                                           Text(text = stringResource(R.string.no_complications), color = colorResource(R.color.disabled_color),
                                               style = MaterialTheme.typography.caption,
                                               modifier = Modifier
                                                   .fillMaxWidth()
                                                   .padding(16.dp, 0.dp))
                                       } else {
                                           visitState.complications.value.forEach { complication ->
                                               ItemListComplicationsDisabled(
                                                   complication = complication,
                                                   checked = complication.selected,
                                                   onCheckedChange = {
                                                       val complicationsToUpdate: MutableList<Complication> = mutableListOf()
                                                       visitState.complications.value.forEach { item ->
                                                           if (item.id == complication.id) {
                                                               item.selected = it
                                                           }
                                                           complicationsToUpdate.add(item)
                                                       }
                                                       visitState.complications.value = mutableListOf()
                                                       visitState.complications.value.addAll(complicationsToUpdate)
                                                   }
                                               )
                                           }
                                       }
                                   }
                               }

                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty())) {
                                   CurrenStatusView(visitState = visitState)
                               }

                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
                                   Column{
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step2_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }
                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
                                   Card(modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(0.dp, 0.dp),
                                       elevation = 0.dp,
                                       backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                                   ) {
                                       Column(
                                           horizontalAlignment = Alignment.CenterHorizontally,
                                           verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                           modifier = Modifier
                                               .wrapContentSize()
                                               .padding(0.dp, 16.dp)
                                       ) {


                                           AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                                               ItemViewImage(visitState.selectedRespiration.value, stringResource(R.string.respiration), R.mipmap.ic_respiration)
                                           }

                                           AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                                               ItemViewImage(visitState.selectedApetit.value, stringResource(R.string.apetit), R.mipmap.ic_apetit)
                                           }

                                           ItemViewImage(visitState.selectedInfection.value, stringResource(R.string.infection), R.mipmap.ic_infeccion)

                                           ItemViewIcon(visitState.selectedEyes.value, stringResource(R.string.eyes), Icons.Filled.RemoveRedEye)

                                           ItemViewImage(visitState.selectedDeshidratation.value, stringResource(R.string.deshidratation), R.mipmap.ic_deshidratation)

                                           ItemViewImage(visitState.selectedVomitos.value, stringResource(R.string.vomits), R.mipmap.ic_vomit)

                                           ItemViewImage(visitState.selectedDiarrea.value, stringResource(R.string.diarrea), R.mipmap.ic_diarrea)

                                           ItemViewImage(visitState.selectedFiebre.value, stringResource(R.string.fiebre), R.mipmap.ic_tos)

                                           AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                                               ItemViewImage(visitState.selectedTos.value, stringResource(R.string.tos), R.mipmap.ic_fiebre)
                                           }

                                           ItemViewIcon(visitState.selectedTemperature.value, stringResource(R.string.temperature), Icons.Filled.Thermostat)

                                       }

                                   }
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                                   Column{
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step3_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }
                               }


                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && visitState.visitNumber.value == 1) {

                                   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 8.dp)) {
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Icon(painterResource(R.mipmap.ic_vitamine), null, tint = colorResource(R.color.colorPrimary))
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(stringResource(R.string.vitamine_a_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                   }

                               }


                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && visitState.visitNumber.value == 1) {
                                   Column(
                                       modifier = Modifier
                                           .wrapContentSize()
                                           .padding(0.dp, 16.dp)
                                   ) {

                                       ItemViewIcon(visitState.selectedVitamineAVaccinated.value, stringResource(R.string.vitamineAVaccinated), Icons.Filled.Book)

                                       AnimatedVisibility(visitState.selectedVitamineAVaccinated.value != stringArrayResource(R.array.yesnooptions)[1]) {
                                           Spacer(modifier = Modifier.height(16.dp))
                                       }
                                       AnimatedVisibility(visitState.selectedVitamineAVaccinated.value != stringArrayResource(R.array.yesnooptions)[1]) {
                                           Column(
                                               modifier = Modifier
                                                   .wrapContentSize()
                                                   .padding(16.dp, 0.dp)
                                           ) {
                                               Text(stringResource(R.string.vitamine_dosis),
                                                   color = colorResource(R.color.disabled_color)
                                               )
                                               if (visitState.weight.value.toDouble() in 6.0..8.0 || (monthsBetween >= 6 && monthsBetween <= 11)) {
                                                   Text(stringResource(R.string.vitamine_blue),
                                                       color = colorResource(R.color.black_gray)
                                                   )
                                               } else if (visitState.weight.value.toDouble() > 8.0 || (monthsBetween >= 12)) {
                                                   Text(stringResource(R.string.vitamine_red),
                                                       color = colorResource(R.color.black_gray)
                                                   )
                                               }
                                           }
                                       }
                                   }
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       &&  (visitState.status.value == stringResource(R.string.aguda_severa)
                                       && visitState.visitNumber.value == 2)) {
                                   Column{
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step3_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && visitState.visitsSize.value == 0) {
                                   if ((monthsBetween >= 12 && monthsBetween < 24) || (monthsBetween >= 24)) {
                                       Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
                                           Spacer(modifier = Modifier.width(8.dp))
                                           Icon(painterResource(R.mipmap.ic_capsules), null, tint = colorResource(R.color.colorPrimary))
                                           Spacer(modifier = Modifier.width(8.dp))
                                           Text(stringResource(R.string.albendazole_a_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                           Spacer(modifier = Modifier.width(16.dp))
                                       }
                                   }
                               }


                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && (visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && visitState.visitNumber.value == 1) || (visitState.status.value == stringResource(R.string.aguda_severa)
                                       && visitState.visitNumber.value == 2)) {
                                   Column(
                                       modifier = Modifier
                                           .wrapContentSize()
                                           .padding(16.dp, 0.dp)
                                   ) {
                                       if ((monthsBetween >= 12 && monthsBetween < 24)) {
                                           Text(stringResource(R.string.admin_dosis),
                                               color = colorResource(R.color.disabled_color)
                                           )
                                           Text(stringResource(R.string.abendazol_400_half),
                                               color = colorResource(R.color.black_gray)
                                           )
                                           Text("o", color = colorResource(R.color.black_gray))
                                           Text(stringResource(R.string.mebendazol_400_half),
                                               color = colorResource(R.color.black_gray)
                                           )
                                       } else if ((monthsBetween >= 24)) {
                                           Text(stringResource(R.string.admin_dosis),
                                               color = colorResource(R.color.disabled_color)
                                           )
                                           Text(stringResource(R.string.abendazol_400_full),
                                               color = colorResource(R.color.black_gray)
                                           )
                                           Text("o", color = colorResource(R.color.black_gray))
                                           Text(stringResource(R.string.mebendazol_400_full),
                                               color = colorResource(R.color.black_gray)
                                           )
                                       }
                                   }
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                                   Spacer(modifier = Modifier.height(16.dp))
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)) {

                                   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Icon(painterResource(R.mipmap.ic_vitamine), null, tint = colorResource(R.color.colorPrimary))
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(stringResource(R.string.ferro_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                   }

                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                                   Column {
                                       ItemViewIcon(visitState.selectedCapsulesFerro.value, stringResource(R.string.capsules_hierro_folico_checked), Icons.Filled.Medication)
                                       Column(
                                           modifier = Modifier
                                               .wrapContentSize()
                                               .padding(16.dp, 16.dp)
                                       ) {
                                           Text(stringResource(R.string.admin_dosis), color = colorResource(R.color.disabled_color))
                                           if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 10.0)) {
                                               Text(stringResource(R.string.capsules_hierro_folico_one), color = colorResource(R.color.black_gray)
                                               )
                                           } else {
                                               Text(stringResource(R.string.capsules_hierro_folico_tow), color = colorResource(R.color.black_gray))
                                           }
                                       }
                                   }
                               }


                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && monthsBetween >= 9) {
                                   Spacer(modifier = Modifier.height(16.dp))
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && monthsBetween >= 9
                                       && (visitState.visitNumber.value == 1 || visitState.visits.value[0].rubeolaVaccinated != stringArrayResource(id = R.array.yesnooptions)[0]) ) {
                                   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Icon(painterResource(R.mipmap.ic_inyection), null, tint = colorResource(R.color.colorPrimary))
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(stringResource(R.string.vaccine_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
                                   }
                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       && monthsBetween >= 9
                                       && (visitState.visitNumber.value == 1 || visitState.visits.value[0].rubeolaVaccinated != stringArrayResource(id = R.array.yesnooptions)[0]) ) {
                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .wrapContentSize()
                                           .padding(0.dp, 0.dp)
                                   ) {
                                       ItemViewIcon(visitState.selectedCartilla.value, stringResource(R.string.cartilla), Icons.Filled.Book)
                                       AnimatedVisibility(visitState.status.value.isNotEmpty()
                                               && visitState.status.value == stringResource(R.string.aguda_moderada)
                                               && monthsBetween >= 9
                                               && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[1]) {

                                           ItemViewIcon(visitState.selectedRubeola.value, stringResource(R.string.rubeola), Icons.Filled.Vaccines)
                                       }

                                       AnimatedVisibility(visitState.status.value.isNotEmpty()
                                               && visitState.status.value == stringResource(R.string.aguda_moderada)
                                               && monthsBetween >= 9
                                               && visitState.selectedCartilla.value != stringArrayResource(id = R.array.yesnooptions)[1]) {
                                           Column(
                                               horizontalAlignment = Alignment.CenterHorizontally,
                                               verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                               modifier = Modifier
                                                   .wrapContentSize()
                                                   .padding(0.dp, 16.dp)
                                           ) {
                                               Icon(Icons.Filled.Error, null, tint = colorResource(R.color.error))
                                               Text(text = stringResource(R.string.must_rubeola), color = colorResource(R.color.error))
                                           }

                                       }

                                       AnimatedVisibility(visitState.status.value.isNotEmpty()
                                               && visitState.status.value == stringResource(R.string.aguda_moderada)
                                               && monthsBetween >= 9
                                               && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[1]
                                               && visitState.selectedRubeola.value != stringArrayResource(id = R.array.yesnooptions)[1]) {
                                           Column(
                                               horizontalAlignment = Alignment.CenterHorizontally,
                                               verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                               modifier = Modifier
                                                   .wrapContentSize()
                                                   .padding(0.dp, 16.dp)
                                           ) {
                                               Icon(Icons.Filled.Error, null, tint = colorResource(R.color.error))
                                               Text(text = stringResource(R.string.must_rubeola), color = colorResource(R.color.error))
                                           }
                                       }
                                   }

                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       && visitState.visitNumber.value == 1) {
                                   Column {
                                       Spacer(modifier = Modifier.height(16.dp))
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step4_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }

                               }

                               AnimatedVisibility(visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       && visitState.visitNumber.value == 1) {
                                   Column(
                                   ) {
                                       Column(modifier = Modifier
                                           .wrapContentSize()
                                           .padding(16.dp, 16.dp)){
                                           Text(stringResource(R.string.amoxicilina), color = colorResource(R.color.disabled_color))
                                           if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 5.0)) {
                                               Text(stringResource(R.string.amoxicilina_125), color = colorResource(R.color.black_gray))
                                           } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 10.0)) {
                                               Text(stringResource(R.string.amoxicilina_250), color = colorResource(R.color.black_gray))
                                           } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 20.0)) {
                                               Text(stringResource(R.string.amoxicilina_500), color = colorResource(R.color.black_gray))
                                           } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 20.0 && visitState.weight.value.toDouble() < 35.0)) {
                                               Text(stringResource(R.string.amoxicilina_750), color = colorResource(R.color.colorPrimary))
                                           } else {
                                               Text(stringResource(R.string.amoxicilina_1000), color = colorResource(R.color.black_gray))
                                           }
                                       }

                                       Spacer(modifier = Modifier.height(16.dp))
                                       ItemViewIcon(visitState.selectedAmoxicilina.value, stringResource(R.string.another_tratements), Icons.Filled.Medication)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       ItemViewIcon(visitState.othersTratments.value, stringResource(R.string.another_tratements), Icons.Filled.Medication)
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }
                               }


                               ItemViewIcon(visitState.observations.value, stringResource(R.string.observations), Icons.Filled.Edit)

                               Spacer(modifier = Modifier.height(16.dp))

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       )) {
                                   Column{
                                       Spacer(modifier = Modifier.height(16.dp))
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step4_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }

                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_moderada)
                                       )) {

                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(0.dp, 16.dp)
                                   ) {
                                       Text(
                                           text = stringResource(R.string.plumpy_one),
                                           color = colorResource(R.color.black_gray),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.h5,
                                           fontWeight = FontWeight.Bold)

                                       Row(modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                                           horizontalArrangement = Arrangement.SpaceBetween,
                                           verticalAlignment = Alignment.CenterVertically) {
                                           Image(
                                               modifier = Modifier.size(78.dp),
                                               painter = painterResource(id = R.mipmap.ic_plumpy),
                                               contentDescription = null,
                                           )

                                           Text(
                                               text = stringResource(R.string.plumpy_fiveteeen),
                                               color = colorResource(R.color.colorPrimary),
                                               textAlign = TextAlign.Center,
                                               style = MaterialTheme.typography.h5,
                                               fontWeight = FontWeight.Bold)
                                       }

                                   }

                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       )) {
                                   Column{
                                       Spacer(modifier = Modifier.height(16.dp))
                                       Divider(color = Color.Gray, thickness = 1.dp)
                                       Spacer(modifier = Modifier.height(16.dp))
                                       SteptTitle(stringResource(R.string.step4_title))
                                       Spacer(modifier = Modifier.height(16.dp))
                                   }

                               }

                               AnimatedVisibility(visible = (visitState.status.value.isNotEmpty()
                                       && visitState.status.value == stringResource(R.string.aguda_severa)
                                       )) {

                                   Column(
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(0.dp, 16.dp)
                                   ) {
                                       Text(
                                           text = stringResource(R.string.plumpy_two),
                                           color = colorResource(R.color.black_gray),
                                           textAlign = TextAlign.Left,
                                           modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                                           style = MaterialTheme.typography.h5,
                                           fontWeight = FontWeight.Bold)

                                       Row(modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                                           horizontalArrangement = Arrangement.SpaceBetween,
                                           verticalAlignment = Alignment.CenterVertically) {
                                           Image(
                                               modifier = Modifier.size(78.dp),
                                               painter = painterResource(id = R.mipmap.ic_plumpy),
                                               contentDescription = null,
                                           )

                                           if (visitState.weight.value.toDouble() >= 3.0 && visitState.weight.value.toDouble() < 3.5) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_8),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 3.5 && visitState.weight.value.toDouble() < 5.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_10),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 7.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_15),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 7.0 && visitState.weight.value.toDouble() < 10.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_20),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 15.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_30),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 15.0 && visitState.weight.value.toDouble() < 20.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_35),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 20.0 && visitState.height.value.toDouble() < 30.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_40),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 30.0 && visitState.weight.value.toDouble() < 40.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_50),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           } else if (visitState.weight.value.toDouble() >= 40.0 && visitState.weight.value.toDouble() <= 60.0) {
                                               Text(
                                                   text = stringResource(R.string.plumpy_mas_55),
                                                   color = colorResource(R.color.colorPrimary),
                                                   textAlign = TextAlign.Center,
                                                   style = MaterialTheme.typography.h5,
                                                   fontWeight = FontWeight.Bold)
                                           }

                                       }


                                   }

                               }

                               Spacer(modifier = Modifier.height(16.dp))
                           }
                       }
                   }


               }

            }

        }
    }

}

@Composable
fun ItemListComplicationsDisabled(complication: Complication, checked: Boolean, onCheckedChange : (Boolean) -> Unit) {

    val language = LocaleListCompat.getDefault()[0]!!.toLanguageTag()
    var complicationTag = ""
    if (language.contains("es-")) {
        complicationTag = complication.name
    } else if(language.contains("en-")) {
        complicationTag = complication.name_en
    } else {
        complicationTag = complication.name_fr
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Checkbox(
            enabled = false,
            checked = true,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )

        Text(
            color = colorResource(R.color.colorPrimary),
            text = complicationTag,
            style = MaterialTheme.typography.body1,
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

@Composable
fun SteptTitle(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
    }
}

@Composable
fun VisitTitle(visitNumber: Int, color: Color) {
    Text(
        text = "${stringResource(R.string.visit)} ${visitNumber}",
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color)
            .padding(0.dp, 16.dp),
        color = colorResource(R.color.white),
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AddmisionTypeView(type: String){
    Divider(color = colorResource(R.color.disabled_color), thickness = 1.dp)
    Spacer(modifier = Modifier.height(4.dp))
    TextField(value = type,
        enabled = false,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.colorPrimary),
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            cursorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            focusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
        ),
        onValueChange = {},
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        leadingIcon = {
            Icon(Icons.Filled.CalendarViewDay, null, tint = colorResource(R.color.colorPrimary))},
        label = { Text(stringResource(R.string.admissionType), color = colorResource(R.color.disabled_color)) })
    Spacer(modifier = Modifier.height(4.dp))
    Divider(color = colorResource(R.color.disabled_color), thickness = 1.dp)
}

@Composable
fun ItemViewIcon(value: String, label: String, icon: ImageVector) {
    TextField(value = value,
        enabled = false,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.colorPrimary),
            backgroundColor = colorResource(R.color.white),
            cursorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            focusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
        ),
        onValueChange = {},
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        leadingIcon = {
            Icon(icon, null, tint = colorResource(R.color.colorPrimary))},
        label = { Text(label, color = colorResource(R.color.disabled_color)) })
}

@Composable
fun ItemViewImage(value: String, label: String, icon: Int) {
    TextField(value = value,
        enabled = false,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.colorPrimary),
            backgroundColor = colorResource(R.color.white),
            cursorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            focusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
        ),
        onValueChange = {},
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        leadingIcon = {
        Icon(painterResource(icon), null, tint = colorResource(R.color.colorPrimary))},
        label = { Text(label, color = colorResource(R.color.disabled_color)) })
}



