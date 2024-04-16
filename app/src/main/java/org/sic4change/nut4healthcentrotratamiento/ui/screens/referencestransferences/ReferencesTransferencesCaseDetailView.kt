package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import android.annotation.SuppressLint
import android.health.connect.datatypes.DataOrigin
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getMonths
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getYears
import java.text.SimpleDateFormat
import java.util.*
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getMonthsAgo
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations.ColorsTransformation
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.ItemViewIcon
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ReferencesTransferencesCaseDetailView(
    referencesTransferencesState: ReferencesTransferencesState,
    derivation: Derivation,
    child: Child?,
    tutor: Tutor?,
    case: Case?,
    lastVisit: Visit?,
    visits: List<Visit?>,
    origin: Point?,
    loading: Boolean = false,
    onCreateVisit: (Derivation, String, String, String) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DerivationView(
                    referencesTransferencesState = referencesTransferencesState,
                    loading = loading,
                    derivation =  derivation,
                    child = child,
                    fefa = tutor,
                    case =  case,
                    lastVisit = lastVisit,
                    visits = visits,
                    origin = origin,
                    onCreateVisit = onCreateVisit
                )

            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun DerivationView(
    referencesTransferencesState: ReferencesTransferencesState,
    loading: Boolean,
    derivation: Derivation,
    child: Child?,
    fefa: Tutor?,
    case: Case?,
    lastVisit: Visit?,
    visits: List<Visit?>,
    origin: Point?,
    onCreateVisit: (Derivation, String, String, String) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shapeDefault = RoundedCornerShape(8.dp)

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    @Composable
    fun getAgeChildFormatted(child: Child): String {
        if (child == null) {
            return ""
        } else {
            return "${SimpleDateFormat("dd/MM/yyyy").format(child.birthdate)}!≈${getYears(child.birthdate)} ${stringResource(R.string.years)} ${getMonths(child.birthdate)} ${stringResource(R.string.months)}"
        }
    }

    @Composable
    fun getAgeFEFAFormatted(fefa: Tutor): String {
        if (fefa == null) {
            return ""
        } else {
            return "${SimpleDateFormat("dd/MM/yyyy").format(fefa.birthdate)}!≈${getYears(fefa.birthdate)} ${stringResource(R.string.years)} ${getMonths(fefa.birthdate)} ${stringResource(R.string.months)}"
        }
    }

    fun getDateFormatted(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getComplications(lastVisit: Visit?): String {
        var complications = ""
        if (lastVisit != null) {
            lastVisit!!.complications.forEach { complication ->
                complications += complication.name + " / "
            }
            if (complications.isNotEmpty()) {
                complications = complications.substring(0, complications.length - 3)
            }
        }
        return complications
    }

    if (!loading) {
        /*if (referencesTransferencesState.showConfirmationDialog.value) {
            SuccessDialog(
                type = referencesTransferencesState.type.value,
                onDismiss = { onCreateDerivationSucessfull(referencesTransferencesState.case.value!!.id) },
                code = referencesTransferencesState.getCode())
        }*/

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {

            item {

                Column {

                    Spacer(modifier = Modifier.height(8.dp))

                    if (child != null) {
                        ChildSummaryItem(
                            item = child,
                            expanded = referencesTransferencesState.expandedDetail.value,
                            onExpandDetail = { referencesTransferencesState.expandContractDetail() }
                        )
                        if (referencesTransferencesState.expandedDetail.value) {
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
                                    if (child.sex == SEXS[0]) Icon(Icons.Filled.Female, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })
                                    else Icon(Icons.Filled.Male, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                            expanded = referencesTransferencesState.expandedDetail.value,
                            onExpandDetail = { referencesTransferencesState.expandContractDetail() }
                        )


                        if (referencesTransferencesState.expandedDetail.value) {
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
                                    Icon(Icons.Default.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                    Icon(Icons.Default.Cake, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                    Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                    Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                    Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                            modifier = Modifier.clickable {  })
                                    } else if (fefa.sex == SEXS[1]) {
                                        Icon(
                                            Icons.Filled.Male,
                                            null,
                                            tint = colorResource(R.color.colorPrimary),
                                            modifier = Modifier.clickable {  })
                                    } else {
                                        Icon(
                                            painterResource(R.mipmap.ic_sex_selection),
                                            null,
                                            tint = colorResource(R.color.colorPrimary),
                                            modifier = Modifier.clickable {  })
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
                                        Icon(painterResource(R.mipmap.ic_relation), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                        Icon(Icons.Filled.PregnantWoman, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                        Icon(Icons.Filled.ViewWeek, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                        Icon(Icons.Filled.ChildCare, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                        Icon(Icons.Filled.ChildFriendly, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
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
                                    Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                                label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        stringResource(R.string.case_info),
                        color = colorResource(R.color.disabled_color),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )

                    TextField(
                        value = getDateFormatted(derivation.createdate),
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                if (derivation.type == "Referred") stringResource(R.string.reference_date) else stringResource(R.string.transference_date),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = child?.let { "${it.name} ${it.surnames}" } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_child_name),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = if (child != null) getAgeChildFormatted(child!!) else getAgeFEFAFormatted(fefa!!),
                        visualTransformation = ColorsTransformation(),
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.birthdate),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = child?.let { it.sex } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.sex),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = fefa?.let { "${it.name} ${it.surnames}" } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_tutor_name),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = fefa?.let { it.phone } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_tutor_phone),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = getDateFormatted(Date()),
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.admission_date),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                }

            }

            item {

                Column {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        if (derivation.type == "Referred") stringResource(R.string.antropometric_reference_date) else stringResource(R.string.antropometric_transference_date),
                        color = colorResource(R.color.disabled_color),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )

                    Column(horizontalAlignment = Alignment.Start) {
                        if (origin!!.type == "CRENAM") {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Box(modifier = Modifier.weight(1f)) {
                                    TextField(
                                        value = lastVisit!!.weight.toString(),
                                        onValueChange = { },
                                        enabled = false,
                                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = colorResource(R.color.colorPrimary),
                                            backgroundColor = colorResource(R.color.full_transparent),
                                            cursorColor = colorResource(R.color.full_transparent),
                                            disabledTextColor = colorResource(R.color.colorPrimary),
                                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                                        ),
                                        label = {
                                            Text(
                                                stringResource(R.string.weight),
                                                color = colorResource(R.color.disabled_color)
                                            )
                                        }
                                    )
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    TextField(
                                        value = lastVisit!!.height.toString(),
                                        onValueChange = { },
                                        enabled = false,
                                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = colorResource(R.color.colorPrimary),
                                            backgroundColor = colorResource(R.color.full_transparent),
                                            cursorColor = colorResource(R.color.full_transparent),
                                            disabledTextColor = colorResource(R.color.colorPrimary),
                                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                                        ),
                                        label = {
                                            Text(
                                                stringResource(R.string.height),
                                                color = colorResource(R.color.disabled_color)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            if (origin.type == "CRENAM") {
                                Box(modifier = Modifier.weight(1f)) {
                                    TextField(
                                        value = lastVisit!!.imc.toString(),
                                        onValueChange = { },
                                        enabled = false,
                                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = colorResource(R.color.colorPrimary),
                                            backgroundColor = colorResource(R.color.full_transparent),
                                            cursorColor = colorResource(R.color.full_transparent),
                                            disabledTextColor = colorResource(R.color.colorPrimary),
                                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                                        ),
                                        label = {
                                            Text("P/T (Z-score)",
                                                color = colorResource(R.color.disabled_color)
                                            )
                                        }
                                    )
                                }
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                TextField(
                                    value = lastVisit!!.armCircunference!!.toString(),
                                    onValueChange = { },
                                    enabled = false,
                                    textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                                        .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = colorResource(R.color.colorPrimary),
                                        backgroundColor = colorResource(R.color.full_transparent),
                                        cursorColor = colorResource(R.color.full_transparent),
                                        disabledTextColor = colorResource(R.color.colorPrimary),
                                        disabledIndicatorColor = colorResource(R.color.full_transparent),
                                        disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        focusedIndicatorColor = colorResource(R.color.full_transparent),
                                        unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                                    ),
                                    label = {
                                        Text(stringResource(R.string.arm_circunference),
                                            color = colorResource(R.color.disabled_color)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    TextField(
                        value = lastVisit?.let { it.edema } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.edema),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = getComplications(lastVisit),
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.complications),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

            item {

                if (visits.isNotEmpty() && visits.size > 1) {
                    Column {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            stringResource(R.string.step3_title),
                            color = colorResource(R.color.disabled_color),
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                        )

                        Column(horizontalAlignment = Alignment.Start) {
                            var i = 1
                            for (visit in visits.subList(1, visits.size)) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(stringResource(R.string.visit) + " $i",
                                    color = colorResource(R.color.disabled_color),
                                    style = MaterialTheme.typography.caption,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                                )
                                SystemicTreatmentView(child!!, visit!!, i)
                                i++
                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }


            }

            item {
                val name = "${stringResource(R.string.caso)}_1"
                val status = stringResource(R.string.open)
                Column {

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),

                        onClick = {

                            onCreateVisit(derivation, name, status, derivation.type)
                        },
                    ) {
                        Text(text = stringResource(R.string.create_visit), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    }
}


@Composable
fun SystemicTreatmentView(child: Child, visit: Visit, visitNumber: Int) {

    val monthsBetween = getMonthsAgo(child.birthdate.time)

    AnimatedVisibility(visit.status.isNotEmpty()
            && visit.status == stringResource(R.string.aguda_moderada)
            && visitNumber == 1) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp, 0.dp)) {
            Text(stringResource(R.string.vitamine_a_title), color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
        }

    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && visit.status == stringResource(R.string.aguda_moderada)
            && visitNumber == 0) {
        if ((monthsBetween >= 12 && monthsBetween < 24) || (monthsBetween >= 24)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp, 0.dp)) {
                Text(stringResource(R.string.albendazole_a_title), color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && (visit.status == stringResource(R.string.aguda_moderada)
            && visitNumber == 1) || (visit.status == stringResource(R.string.aguda_severa)
            && visitNumber == 2)) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp, 0.dp)
        ) {
            if ((monthsBetween >= 12 && monthsBetween < 24)) {
                Text(stringResource(R.string.abendazol_400_half) + "/" + stringResource(R.string.mebendazol_400_half),
                    color = colorResource(R.color.colorPrimary)
                )
            } else if ((monthsBetween >= 24)) {
                Text(stringResource(R.string.abendazol_400_full) + "/" + stringResource(R.string.mebendazol_400_full),
                    color = colorResource(R.color.colorPrimary)
                )
            }
        }
    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && visit.status == stringResource(R.string.aguda_moderada)) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp, 0.dp)) {
            Text(stringResource(R.string.ferro_title), color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
        }

    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && visit.status == stringResource(R.string.aguda_moderada)) {
        Column {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp, 0.dp)
            ) {
                if ((visit.weight > 0.0 && visit.weight < 10.0)) {
                    Text(stringResource(R.string.capsules_hierro_folico_one), color = colorResource(R.color.colorPrimary)
                    )
                } else {
                    Text(stringResource(R.string.capsules_hierro_folico_tow), color = colorResource(R.color.colorPrimary))
                }
            }
        }
    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && visit.status == stringResource(R.string.aguda_severa)
            && visitNumber == 1) {
        Column(
        ) {
            Column(modifier = Modifier
                .wrapContentSize()
                .padding(16.dp, 0.dp)){
                Text(stringResource(R.string.amoxicilina).split(": ")[0].trim(), color = colorResource(R.color.colorPrimary))
                if ((visit.weight > 0.0 && visit.weight < 5.0)) {
                    Text(stringResource(R.string.amoxicilina_125), color = colorResource(R.color.colorPrimary))
                } else if ((visit.weight > 0.0 && visit.weight >= 5.0 && visit.weight < 10.0)) {
                    Text(stringResource(R.string.amoxicilina_250), color = colorResource(R.color.colorPrimary))
                } else if ((visit.weight > 0.0 && visit.weight >= 10.0 && visit.weight < 20.0)) {
                    Text(stringResource(R.string.amoxicilina_500), color = colorResource(R.color.colorPrimary))
                } else if ((visit.weight > 0.0 && visit.weight >= 20.0 && visit.weight < 35.0)) {
                    Text(stringResource(R.string.amoxicilina_750), color = colorResource(R.color.colorPrimary))
                } else {
                    Text(stringResource(R.string.amoxicilina_1000), color = colorResource(R.color.colorPrimary))
                }
            }

            if (visit.amoxicilina.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                ItemViewIcon(visit.amoxicilina, stringResource(R.string.another_tratements), Icons.Filled.Medication)
            }

            if (visit.otherTratments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                ItemViewIcon(visit.otherTratments, stringResource(R.string.another_tratements), Icons.Filled.Medication)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    AnimatedVisibility(visit.vitamineAVaccinated == stringArrayResource(id = R.array.yesnooptions)[2]) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp, 0.dp)
        ) {
            if ((monthsBetween in 6..11) || (visit.weight > 0.0 && visit.weight >= 6.0 && visit.weight <= 8.0)) {
                Text(stringResource(
                    R.string.vitamine_blue),
                    color = colorResource(R.color.colorPrimary)
                )
            } else if ((monthsBetween >= 12) || (visit.weight > 0.0 && visit.weight > 8.0)) {
                Text(stringResource(
                    R.string.vitamine_red),
                    color = colorResource(R.color.colorPrimary)
                )
            }
        }
    }

}

/*@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(R.mipmap.ic_ref_derivation_form),
        contentDescription = null,
        modifier =  modifier
    )
}*/

/*@Composable
fun SuccessDialog(
    type: String,
    code: String ="Code",
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(370.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .background(
                            color = colorResource(R.color.white),
                            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            if (type == "Referred") stringResource(R.string.reference_succesful) else stringResource(R.string.transference_succesful),
                            color = colorResource(R.color.disabled_color),
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            if (type == "Referred") stringResource(R.string.reference_code).uppercase(Locale.getDefault()) else stringResource(R.string.transference_code).uppercase(Locale.getDefault()),
                            color = colorResource(R.color.black_gray),
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center)

                        Text(
                            code.uppercase(Locale.getDefault()),
                            color = colorResource(R.color.error),
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth().clickable {
                                onDismiss()
                            }
                        ) {
                            Image(
                                painter = painterResource(R.mipmap.ic_check_derivation_form),
                                contentDescription = null,
                                modifier =  Modifier.size(72.dp)
                            )
                            Text(
                                stringResource(R.string.go_back),
                                color = colorResource(R.color.black_gray),
                                fontSize = MaterialTheme.typography.caption.fontSize,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center)
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}*/


/*class ColorsTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.toString()),
            OffsetMapping.Identity)
    }

    fun buildAnnotatedStringWithColors(text:String): AnnotatedString{
        val words: List<String> = text.split("!")// splits by whitespace
        val colors = listOf(Color(android.graphics.Color.parseColor("#379FF3")), Color(android.graphics.Color.parseColor("#9EC830")))
        var count = 0

        val builder = AnnotatedString.Builder()
        for (word in words) {
            builder.withStyle(style = SpanStyle(color = colors[count%2])) {
                append("$word ")
            }
            count ++
        }
        return builder.toAnnotatedString()
    }
}*/





