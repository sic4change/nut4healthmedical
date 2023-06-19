package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.LocaleListCompat
import coil.annotation.ExperimentalCoilApi
import com.maryamrzdh.stepper.Stepper
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.commons.CheckNUT4H
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildSummaryItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitListItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitItemCreateScreen(visitState: VisitState, loading: Boolean = false, child: Child?,
                          onCreateVisit: (Double, Double, Double, String, String, String, String,
                                          String, String, String, String, String, String, String,
                                          String, Boolean, Boolean, String, String, Boolean, String,
                                          complications: List<Complication>, String) -> Unit,
                          onChangeWeightOrHeight: (String, String, String, List<Complication>) -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (loading) {
            CircularProgressIndicator(color = colorResource(R.color.colorPrimary))
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            VisitView(
                loading = loading,
                visitState = visitState,
                child = child,
                onCreateVisit = onCreateVisit,
                onChangeWeightOrHeight = onChangeWeightOrHeight
            )

        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun VisitView(loading: Boolean, visitState: VisitState, child: Child?,
                      onCreateVisit: (
                       Double, Double, Double, String, String, String, String,
                       String, String, String, String, String, String, String,
                       String, Boolean, Boolean, String, String, Boolean, String,
                       complications: List<Complication>, String) -> Unit,
                      onChangeWeightOrHeight: (String, String, String, List<Complication>) -> Unit) {

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    val numberStep = 4
    val titleList= arrayListOf(
        stringResource(R.string.step1),
        stringResource(R.string.step2),
        stringResource(R.string.step3),
        stringResource(R.string.step4)
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (!loading) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            //if (child != null) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    ChildSummaryItem(
                        item = child!!,
                        expanded = visitState.expandedDetail.value,
                        onExpandDetail = { visitState.expandContractDetail() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Stepper(
                        modifier = Modifier.fillMaxWidth(),
                        numberOfSteps = numberStep,
                        currentStep = visitState.currentStep.value,
                        stepDescriptionList = titleList,
                        selectedColor = colorResource(R.color.colorPrimary),
                        unSelectedColor= colorResource(R.color.disabled_color)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    if (visitState.expandedDetail.value) {
                        TextField(value = child!!.brothers.toString(),
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
                item {
                    Text(stringResource(R.string.create_visit),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (visitState.currentStep.value == 1) {
                        AntropometricosView(visitState, onChangeWeightOrHeight)
                    }
                    else if (visitState.currentStep.value == 2) {
                        SymtomsView(visitState)
                    }
                    else if (visitState.currentStep.value == 3) {
                        SistemicView(visitState)
                    }
                    else if (visitState.currentStep.value == 4) {
                        NutritionalView(visitState)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty())) {
                        val lastStep = (visitState.status.value == stringResource(R.string.normopeso) ||
                            visitState.status.value == stringResource(R.string.objetive_weight))
                        Button(
                            enabled = !visitState.createdVisit.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                            onClick = {
                                if (lastStep) {
                                    onCreateVisit(visitState.height.value.filter { !it.isWhitespace() }.toDouble(),
                                        visitState.weight.value.filter { !it.isWhitespace() }.toDouble(),
                                        visitState.armCircunference.value, visitState.status.value, visitState.selectedEdema.value,
                                        visitState.selectedRespiration.value, visitState.selectedApetit.value,
                                        visitState.selectedInfection.value, visitState.selectedEyes.value,
                                        visitState.selectedDeshidratation.value, visitState.selectedVomitos.value,
                                        visitState.selectedDiarrea.value, visitState.selectedFiebre.value,
                                        visitState.selectedTos.value, visitState.selectedTemperature.value,
                                        visitState.vitamineAVaccinated.value, visitState.capsulesFerro.value,
                                        visitState.selectedCartilla.value, visitState.selectedRubeola.value,
                                        visitState.amoxicilina.value, visitState.othersTratments.value,
                                        visitState.complications.value, visitState.observations.value)
                                } else {
                                    if (visitState.currentStep.value == 4) {
                                        visitState.createdVisit.value = true
                                        if (visitState.selectedCartilla.value == "" && visitState.visits.value.size > 0) {
                                            visitState.selectedCartilla.value = visitState.visits.value[0].vaccinationCard
                                        }

                                        if (visitState.selectedRubeola.value == "" && visitState.visits.value.size > 0) {
                                            visitState.selectedRubeola.value = visitState.visits.value[0].rubeolaVaccinated
                                        }

                                        if (!visitState.vitamineAVaccinated.value && visitState.visits.value.size > 0) {
                                            visitState.vitamineAVaccinated.value = visitState.visits.value[0].vitamineAVaccinated
                                        }

                                        onCreateVisit(visitState.height.value.filter { !it.isWhitespace() }.toDouble(),
                                            visitState.weight.value.filter { !it.isWhitespace() }.toDouble(),
                                            visitState.armCircunference.value, visitState.status.value, visitState.selectedEdema.value,
                                            visitState.selectedRespiration.value, visitState.selectedApetit.value,
                                            visitState.selectedInfection.value, visitState.selectedEyes.value,
                                            visitState.selectedDeshidratation.value, visitState.selectedVomitos.value,
                                            visitState.selectedDiarrea.value, visitState.selectedFiebre.value,
                                            visitState.selectedTos.value, visitState.selectedTemperature.value,
                                            visitState.vitamineAVaccinated.value, visitState.capsulesFerro.value,
                                            visitState.selectedCartilla.value, visitState.selectedRubeola.value,
                                            visitState.amoxicilina.value, visitState.othersTratments.value,
                                            visitState.complications.value, visitState.observations.value)
                                    } else {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(index = 0)
                                        }
                                        visitState.incrementStep()
                                    }
                                }


                            },
                        ) {
                            Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
                        }
                    }

                    AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() )) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
           // }

        }
    }

}


@Composable
fun ItemListComplications(complication: Complication, checked: Boolean, onCheckedChange : (Boolean) -> Unit) {

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
            .padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                }),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Checkbox(
            checked = checked,
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


@ExperimentalCoilApi
@Composable
fun CurrenStatusView(
    visitState: VisitState,
    modifier: Modifier = Modifier
) {
    var colorBackground : Color = Color.White
    if (formatStatus(visitState.status.value)  == stringResource(R.string.normopeso)) {
        colorBackground = colorResource(R.color.colorAccent)
    } else if (formatStatus(visitState.status.value)  == stringResource(R.string.objetive_weight)) {
        colorBackground = colorResource(R.color.colorPrimary)
    } else if (formatStatus(visitState.status.value)  == stringResource(R.string.aguda_moderada)) {
        colorBackground = colorResource(R.color.orange)
    } else {
        colorBackground = colorResource(R.color.error)
    }
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card(
            backgroundColor = colorBackground,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        tint = colorResource(R.color.white),
                        imageVector = Icons.Default.Folder,
                        contentDescription = null
                    )
                }
                Text(
                    color = colorResource(R.color.white),
                    text = "${formatStatus(visitState.status.value) }".toString().capitalize()  ,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(8.dp, 16.dp)
                        .weight(1f)
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NutritionalView(visitState: VisitState) {
    CurrenStatusView(visitState = visitState)
    Spacer(modifier = Modifier.height(16.dp))
    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && (visitState.status.value == stringResource(R.string.aguda_moderada)
            || visitState.status.value == stringResource(R.string.aguda_severa))){
        Spacer(modifier = Modifier.height(16.dp))
        SteptTitle(R.mipmap.ic_step_four, stringResource(R.string.step4_title))
        Spacer(modifier = Modifier.height(32.dp))
    }

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visible = (
            visitState.weight.value.isNotEmpty()
                    && visitState.height.value.isNotEmpty()
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

    AnimatedVisibility(visible = (
            visitState.weight.value.isNotEmpty()
                    && visitState.height.value.isNotEmpty()
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

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SistemicView(visitState: VisitState) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateString = simpleDateFormat.format(visitState.childDateMillis.value)

    val monthsBetween = ChronoUnit.MONTHS.between(
        YearMonth.from(LocalDate.parse(dateString)), YearMonth.from(LocalDate.now())
    )

    CurrenStatusView(visitState = visitState)
    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && (visitState.status.value == stringResource(R.string.aguda_moderada)
            || visitState.status.value == stringResource(R.string.aguda_severa))){
        Spacer(modifier = Modifier.height(32.dp))
        SteptTitle(R.mipmap.ic_step_three, stringResource(R.string.step3_title))
        Spacer(modifier = Modifier.height(32.dp))
    }

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)
            && visitState.visitsSize.value == 0) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.vitamine_a_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.height(16.dp))

    }


    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)
            && visitState.visitsSize.value == 0) {
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
                    .padding(0.dp, 16.dp)
            ) {

                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    CheckNUT4H(text = stringResource(id = R.string.vitamineAVaccinated), visitState.vitamineAVaccinated.value) {
                        visitState.vitamineAVaccinated.value = it
                    }
                }

                AnimatedVisibility(visitState.vitamineAVaccinated.value) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visitState.vitamineAVaccinated.value) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 0.dp)
                    ) {
                        Text(stringResource(
                            R.string.vitamine_dosis),
                            color = colorResource(R.color.black)
                        )
                        if (visitState.weight.value.toDouble() in 6.0..8.0 || (monthsBetween >= 6 && monthsBetween <= 11)) {
                            Text(
                                stringResource(R.string.vitamine_blue),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else if (visitState.weight.value.toDouble() > 8.0 || (monthsBetween >= 12)) {
                            Text(stringResource(
                                R.string.vitamine_red),
                                color = colorResource(R.color.colorPrimary)
                            )
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && (visitState.status.value == stringResource(R.string.aguda_moderada)
            && visitState.visitsSize.value == 0) || (visitState.status.value == stringResource(R.string.aguda_severa)
            && visitState.visitsSize.value == 1)) {
        Spacer(modifier = Modifier.height(16.dp))
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)
            && visitState.visitsSize.value == 0) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.albendazole_a_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.height(16.dp))

    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && (visitState.status.value == stringResource(R.string.aguda_moderada)
            && visitState.visitsSize.value == 0) || (visitState.status.value == stringResource(R.string.aguda_severa)
            && visitState.visitsSize.value == 1)) {
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
                    .padding(0.dp, 16.dp)
            ) {
                if ((monthsBetween >= 12 && monthsBetween < 24)) {
                    Text(
                        stringResource(R.string.admin_dosis),
                        color = colorResource(R.color.black)
                    )
                    Text(
                        stringResource(R.string.abendazol_400_half),
                        color = colorResource(R.color.colorPrimary)
                    )
                    Text("o", color = colorResource(R.color.colorPrimary))
                    Text(
                        stringResource(R.string.mebendazol_400_half),
                        color = colorResource(R.color.colorPrimary)
                    )
                } else if ((monthsBetween >= 24)) {
                    Text(
                        stringResource(R.string.admin_dosis),
                        color = colorResource(R.color.black)
                    )
                    Text(
                        stringResource(R.string.abendazol_400_full),
                        color = colorResource(R.color.colorPrimary)
                    )
                    Text("o", color = colorResource(R.color.colorPrimary))
                    Text(
                        stringResource(R.string.mebendazol_400_full),
                        color = colorResource(R.color.colorPrimary)
                    )
                }
            }
        }
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)) {
        Spacer(modifier = Modifier.height(16.dp))
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.ferro_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.height(16.dp))

    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            elevation = 0.dp,
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterVertically
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(0.dp, 16.dp)
            ) {
                Text(
                    stringResource(R.string.admin_dosis),
                    color = colorResource(R.color.black)
                )
                if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 10.0)) {
                    Text(
                        stringResource(R.string.capsules_hierro_folico_one),
                        color = colorResource(R.color.colorPrimary)
                    )
                } else {
                    Text(
                        stringResource(R.string.capsules_hierro_folico_tow),
                        color = colorResource(R.color.colorPrimary)
                    )
                }
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    CheckNUT4H(text = stringResource(id = R.string.capsules_hierro_folico_checked), visitState.capsulesFerro.value) {
                        visitState.capsulesFerro.value = it
                    }
                }

            }
        }
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)
            && monthsBetween >= 9
            && (visitState.visitsSize.value == 0 || visitState.visits.value[0].rubeolaVaccinated != stringArrayResource(id = R.array.yesnooptions)[0]) ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 16.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.vaccine_title), color = colorResource(R.color.disabled_color), style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_moderada)
            && monthsBetween >= 9
            && (visitState.visitsSize.value == 0 || visitState.visits.value[0].rubeolaVaccinated != stringArrayResource(id = R.array.yesnooptions)[0]) ) {
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
                    .padding(0.dp, 16.dp)
            ) {
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedCartilla.value,
                    onExpandedChange = {
                        visitState.expandedCartilla.value = !visitState.expandedCartilla.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedCartilla.value,
                        onValueChange = {
                            visitState.selectedCartilla.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedCartilla.value
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
                            Icon(Icons.Filled.Book, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.cartilla), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedCartilla.value,
                        onDismissRequest = {
                            visitState.expandedCartilla.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.yesnooptions).forEach { selectedEdema ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedCartilla.value = selectedEdema
                                    visitState.expandedCartilla.value = false
                                }
                            ) {
                                Text(text = selectedEdema, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }
                AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                        && visitState.status.value == stringResource(R.string.aguda_moderada)
                        && monthsBetween >= 9
                        && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[0]) {

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedRubeola.value,
                        onExpandedChange = {
                            visitState.expandedRubeola.value = !visitState.expandedRubeola.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedRubeola.value,
                            onValueChange = {
                                visitState.selectedRubeola.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedRubeola.value
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
                                Icon(Icons.Filled.Vaccines, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.rubeola), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedRubeola.value,
                            onDismissRequest = {
                                visitState.expandedRubeola.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.yesnooptions).forEach { selectedEdema ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedRubeola.value = selectedEdema
                                        visitState.expandedRubeola.value = false
                                    }
                                ) {
                                    Text(text = selectedEdema, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }
                }

                AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                        && visitState.status.value == stringResource(R.string.aguda_moderada)
                        && monthsBetween >= 9
                        && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[1]) {
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

                AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                        && visitState.status.value == stringResource(R.string.aguda_moderada)
                        && monthsBetween >= 9
                        && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[0]
                        && visitState.selectedRubeola.value == stringArrayResource(id = R.array.yesnooptions)[1]) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.must_rubeola),
                            color = colorResource(R.color.colorPrimary),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }

    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_severa)
            && visitState.visitsSize.value == 0) {
        Spacer(modifier = Modifier.height(16.dp))
    }

    AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value == stringResource(R.string.aguda_severa)
            && visitState.visitsSize.value == 0) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            elevation = 0.dp,
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterVertically
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(0.dp, 16.dp)
            ) {
                Text(
                    stringResource(R.string.amoxicilina),
                    color = colorResource(R.color.colorPrimary)
                )
                if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 5.0)) {
                    Text(
                        stringResource(R.string.amoxicilina_125),
                        color = colorResource(R.color.colorPrimary)
                    )
                } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 10.0)) {
                    Text(
                        stringResource(R.string.amoxicilina_250),
                        color = colorResource(R.color.colorPrimary)
                    )
                } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 20.0)) {
                    Text(
                        stringResource(R.string.amoxicilina_500),
                        color = colorResource(R.color.colorPrimary)
                    )
                } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 20.0 && visitState.weight.value.toDouble() < 35.0)) {
                    Text(
                        stringResource(R.string.amoxicilina_750),
                        color = colorResource(R.color.colorPrimary)
                    )
                } else {
                    Text(
                        stringResource(R.string.amoxicilina_1000),
                        color = colorResource(R.color.colorPrimary)
                    )
                }
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    CheckNUT4H(text = stringResource(id = R.string.amoxicilina_question), visitState.amoxicilina.value) {
                        visitState.amoxicilina.value = it
                    }
                }
                TextField(value = visitState.othersTratments.value.toString(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(R.color.colorPrimary),
                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                        cursorColor = colorResource(R.color.colorAccent),
                        disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                        focusedIndicatorColor = colorResource(R.color.colorAccent),
                        unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                    ),
                    onValueChange = {
                        visitState.othersTratments.value = it
                    },
                    textStyle = MaterialTheme.typography.h5,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    leadingIcon = {
                        Icon(Icons.Filled.Medication, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                    label = { Text(stringResource(R.string.another_tratements), color = colorResource(R.color.disabled_color)) })

            }
        }
    }




}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SymtomsView(visitState: VisitState) {
    CurrenStatusView(visitState = visitState)
    Spacer(modifier = Modifier.height(16.dp))
    AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
        Spacer(modifier = Modifier.height(32.dp))
        SteptTitle(R.mipmap.ic_step_two, stringResource(R.string.step2_title))
        Spacer(modifier = Modifier.height(32.dp))
    }

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
            && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
        Card(modifier = Modifier
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

                AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedRespiration.value,
                        onExpandedChange = {
                            visitState.expandedRespiration.value = !visitState.expandedRespiration.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedRespiration.value,
                            onValueChange = {
                                visitState.selectedRespiration.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedRespiration.value
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
                                Icon(Icons.Filled.Air, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.respiration), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedRespiration.value,
                            onDismissRequest = {
                                visitState.expandedRespiration.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.respirationOptions).forEach { selectedRespiration ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedRespiration.value = selectedRespiration
                                        visitState.expandedRespiration.value = false
                                    }
                                ) {
                                    Text(text = selectedRespiration, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }
                }


                AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedApetit.value,
                        onExpandedChange = {
                            visitState.expandedApetit.value = !visitState.expandedApetit.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedApetit.value,
                            onValueChange = {
                                visitState.selectedApetit.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedApetit.value
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
                                Icon(Icons.Filled.BreakfastDining, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.apetit), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedApetit.value,
                            onDismissRequest = {
                                visitState.expandedApetit.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.apetitOptions).forEach { selectedRespiration ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedApetit.value = selectedRespiration
                                        visitState.expandedApetit.value = false
                                    }
                                ) {
                                    Text(text = selectedRespiration, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedInfecction.value,
                    onExpandedChange = {
                        visitState.expandedInfecction.value = !visitState.expandedInfecction.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedInfection.value,
                        onValueChange = {
                            visitState.selectedInfection.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedInfecction.value
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
                            Icon(Icons.Filled.RemoveRedEye, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.infection), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedInfecction.value,
                        onDismissRequest = {
                            visitState.expandedInfecction.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedInfection.value = selectedInfection
                                    visitState.expandedInfecction.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedEyes.value,
                    onExpandedChange = {
                        visitState.expandedEyes.value = !visitState.expandedEyes.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedEyes.value,
                        onValueChange = {
                            visitState.selectedEyes.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedEyes.value
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
                            Icon(Icons.Filled.RemoveRedEye, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.eyes), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedEyes.value,
                        onDismissRequest = {
                            visitState.expandedEyes.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedEyes.value = selectedInfection
                                    visitState.expandedEyes.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedDeshidratation.value,
                    onExpandedChange = {
                        visitState.expandedDeshidratation.value = !visitState.expandedDeshidratation.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedDeshidratation.value,
                        onValueChange = {
                            visitState.selectedDeshidratation.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedDeshidratation.value
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
                            Icon(Icons.Filled.WaterDrop, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.deshidratation), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedDeshidratation.value,
                        onDismissRequest = {
                            visitState.expandedDeshidratation.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedDeshidratation.value = selectedInfection
                                    visitState.expandedDeshidratation.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedVomitos.value,
                    onExpandedChange = {
                        visitState.expandedVomitos.value = !visitState.expandedVomitos.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedVomitos.value,
                        onValueChange = {
                            visitState.selectedVomitos.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedVomitos.value
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
                            Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.vomits), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedVomitos.value,
                        onDismissRequest = {
                            visitState.expandedVomitos.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.frecuencyOptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedVomitos.value = selectedInfection
                                    visitState.expandedVomitos.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedDiarrea.value,
                    onExpandedChange = {
                        visitState.expandedDiarrea.value = !visitState.expandedDiarrea.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedDiarrea.value,
                        onValueChange = {
                            visitState.selectedDiarrea.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedDiarrea.value
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
                            Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.diarrea), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedDiarrea.value,
                        onDismissRequest = {
                            visitState.expandedDiarrea.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.frecuencyOptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedDiarrea.value = selectedInfection
                                    visitState.expandedDiarrea.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedFiebre.value,
                    onExpandedChange = {
                        visitState.expandedFiebre.value = !visitState.expandedFiebre.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedFiebre.value,
                        onValueChange = {
                            visitState.selectedFiebre.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedFiebre.value
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
                            Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.fiebre), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedFiebre.value,
                        onDismissRequest = {
                            visitState.expandedFiebre.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.frecuencyOptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedFiebre.value = selectedInfection
                                    visitState.expandedFiebre.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedTos.value,
                        onExpandedChange = {
                            visitState.expandedTos.value = !visitState.expandedTos.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedTos.value,
                            onValueChange = {
                                visitState.selectedTos.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedTos.value
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
                                Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.tos), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedTos.value,
                            onDismissRequest = {
                                visitState.expandedTos.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedTos.value = selectedInfection
                                        visitState.expandedTos.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    expanded = visitState.expandedTemperature.value,
                    onExpandedChange = {
                        visitState.expandedTemperature.value = !visitState.expandedTemperature.value
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = visitState.selectedTemperature.value,
                        onValueChange = {
                            visitState.selectedTemperature.value = it
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = visitState.expandedTemperature.value
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
                            Icon(Icons.Filled.Thermostat, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                        label = { Text(stringResource(R.string.temperature), color = colorResource(R.color.disabled_color)) }
                    )
                    ExposedDropdownMenu(
                        expanded = visitState.expandedTemperature.value,
                        onDismissRequest = {
                            visitState.expandedTemperature.value = false
                        }
                    ) {
                        stringArrayResource(id = R.array.temperatureoptions).forEach { selectedInfection ->
                            DropdownMenuItem(
                                onClick = {
                                    visitState.selectedTemperature.value = selectedInfection
                                    visitState.expandedTemperature.value = false
                                }
                            ) {
                                Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AntropometricosView(visitState: VisitState,
                        onChangeWeightOrHeight: (String, String, String, List<Complication>) -> Unit) {
    SteptTitle(R.mipmap.ic_step_one, stringResource(R.string.step1_title))
    Spacer(modifier = Modifier.height(16.dp))
    TextField(value = visitState.height.value.toString(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.colorPrimary),
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            cursorColor = colorResource(R.color.colorAccent),
            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            focusedIndicatorColor = colorResource(R.color.colorAccent),
            unfocusedIndicatorColor = colorResource(R.color.colorAccent),
        ),
        onValueChange = {
            visitState.formatHeightValue(it)
            onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                visitState.weight.value.filter { !it.isWhitespace() }, visitState.selectedEdema.value,
                visitState.complications.value)
        },
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        leadingIcon = {
            Icon(Icons.Filled.Height, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
        label = { Text(stringResource(R.string.height), color = colorResource(R.color.disabled_color)) })
    Spacer(modifier = Modifier.height(16.dp))
    TextField(value = visitState.weight.value.toString(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.colorPrimary),
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            cursorColor = colorResource(R.color.colorAccent),
            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            focusedIndicatorColor = colorResource(R.color.colorAccent),
            unfocusedIndicatorColor = colorResource(R.color.colorAccent),
        ),
        onValueChange = {
            visitState.formatWeightValue(it)
            onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                visitState.weight.value.filter { !it.isWhitespace() },
                visitState.selectedEdema.value, visitState.complications.value)
        },
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        leadingIcon = {
            Icon(Icons.Filled.SpaceBar, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
        label = { Text(stringResource(R.string.weight), color = colorResource(R.color.disabled_color)) })
    Spacer(modifier = Modifier.height(16.dp))

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateString = simpleDateFormat.format(visitState.childDateMillis.value)

    val monthsBetween = ChronoUnit.MONTHS.between(
        YearMonth.from(LocalDate.parse(dateString)), YearMonth.from(LocalDate.now())
    )

    AnimatedVisibility(visible = ((visitState.armCircunference.value != 30.0) && (monthsBetween >= 6 && monthsBetween <= 60))) {
        if (visitState.armCircunference.value < 11.5) {
            TextField(value = visitState.armCircunference.value.toString(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.error),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.error),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.error),
                    unfocusedIndicatorColor = colorResource(R.color.error),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.MultipleStop, null, tint = colorResource(R.color.error),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })
        }  else if (visitState.armCircunference.value >= 11.5 && visitState.armCircunference.value <= 12.5) {
            TextField(value = visitState.armCircunference.value.toString(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.orange),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.orange),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.orange),
                    unfocusedIndicatorColor = colorResource(R.color.orange),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.MultipleStop, null, tint = colorResource(R.color.orange),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })
        } else {
            TextField(value = visitState.armCircunference.value.toString(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorAccent),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.MultipleStop, null, tint = colorResource(R.color.colorAccent),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })
        }

    }

    AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() && (monthsBetween >= 6 && monthsBetween <= 60))) {
        Spacer(modifier = Modifier.height(16.dp))
    }

    AnimatedVisibility(visible = (monthsBetween >= 6 && monthsBetween <= 60)) {
        AndroidView(
            factory = {
                val view = LayoutInflater.from(it)
                    .inflate(R.layout.muac_view, null, false)
                view
            },
            update  = {view ->
                val ruler = view.findViewById<org.sic4change.nut4healthcentrotratamiento.ui.commons.SimpleRulerViewer>(R.id.ruler)
                val rulerBackground = view.findViewById<View>(R.id.rulerBackground)
                val tvCm = view.findViewById<TextView>(R.id.tvCm)
                val df = DecimalFormat("#.0")
                ruler.setOnValueChangeListener { view, position, value ->
                    tvCm.text = df.format(value).toString() + " cm"
                    visitState.armCircunference.value = df.format(value).replace(",", ".").toDouble()

                    if (value < 11.5
                        || (visitState.selectedEdema.value.isNotEmpty() && visitState.selectedEdema.value != "No")
                        || (visitState.complications.value.any{it.selected})
                    ) {
                        if (value < 11.5) {
                            rulerBackground.setBackgroundResource(R.color.error)
                        }
                        else if (value in 11.5..12.5) {
                            rulerBackground.setBackgroundResource(R.color.orange)
                        }
                        else {
                            rulerBackground.setBackgroundResource(R.color.colorAccent)
                        }
                        tvCm.setTextColor(R.color.error)
                        visitState.status.value = "Aguda Severa"
                    } else if (value in 11.5..12.5) {
                        rulerBackground.setBackgroundResource(R.color.orange)
                        tvCm.setTextColor(R.color.orange)
                        if (visitState.imc.value.equals(-1.5) || visitState.imc.value.equals(80.0) || visitState.imc.value.equals(-1.0) || visitState.imc.value.equals(85.0)
                            || visitState.imc.value.equals(0.0) || visitState.imc.value.equals(100.0)) {
                            visitState.status.value = "Aguda Moderada"
                        }
                    } else {
                        rulerBackground.setBackgroundResource(R.color.colorAccent)
                        tvCm.setTextColor(R.color.colorAccent)
                        if (visitState.imc.value.equals(0.0) || visitState.imc.value.equals(100.0)) {
                            visitState.status.value = "Normopeso"
                        } else if (visitState.imc.value.equals(-1.0) || visitState.imc.value.equals(85.0)) {
                            visitState.status.value = "Peso Objetivo"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
        )
    }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        expanded = visitState.expandedEdema.value,
        onExpandedChange = {
            visitState.expandedEdema.value = !visitState.expandedEdema.value
        }
    ) {
        TextField(
            readOnly = true,
            value = visitState.selectedEdema.value,
            onValueChange = {
                visitState.selectedEdema.value = it
                onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                    visitState.weight.value.filter { !it.isWhitespace() }, visitState.selectedEdema.value,
                    visitState.complications.value)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = visitState.expandedEdema.value
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
                Icon(Icons.Filled.EggAlt, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
            label = { Text(stringResource(R.string.edema), color = colorResource(R.color.disabled_color)) }
        )
        ExposedDropdownMenu(
            expanded = visitState.expandedEdema.value,
            onDismissRequest = {
                visitState.expandedEdema.value = false
            }
        ) {
            stringArrayResource(id = R.array.edemaOptions).forEach { selectedEdema ->
                DropdownMenuItem(
                    onClick = {
                        visitState.selectedEdema.value = selectedEdema
                        visitState.expandedEdema.value = false
                        onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                            visitState.weight.value.filter { !it.isWhitespace() }, visitState.selectedEdema.value,
                            visitState.complications.value)
                    }
                ) {
                    Text(text = selectedEdema, color = colorResource(R.color.colorPrimary))
                }
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
            Text(text = stringResource(R.string.complications), color = colorResource(R.color.disabled_color),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp))

            visitState.complications.value.forEach { complication ->
                ItemListComplications(
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
                        onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                            visitState.weight.value.filter { !it.isWhitespace() }, visitState.selectedEdema.value,
                            visitState.complications.value)
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() )) {
        var statusFormated = ""
        if (visitState.status.value == "Normopeso" || visitState.status.value == stringResource(R.string.normopeso)) {
            statusFormated = stringResource(R.string.normopeso)
        } else if (visitState.status.value == "Peso Objetivo" || visitState.status.value == stringResource(R.string.objetive_weight)) {
            statusFormated = stringResource(R.string.objetive_weight)
        } else if (visitState.status.value == "Aguda Moderada" || visitState.status.value == stringResource(R.string.aguda_moderada)) {
            statusFormated = stringResource(R.string.aguda_moderada)
        } else {
            statusFormated = stringResource(R.string.aguda_severa)
        }

        if (visitState.armCircunference.value < 11.5) {
            statusFormated = stringResource(R.string.aguda_severa)
        } else if (visitState.armCircunference.value >= 11.5 && visitState.armCircunference.value <= 12.5 &&
            (statusFormated == stringResource(R.string.normopeso) || statusFormated == stringResource(R.string.objetive_weight))) {
            statusFormated = stringResource(R.string.aguda_moderada)
        }

        visitState.status.value = statusFormated

        if (visitState.status.value == stringResource(R.string.normopeso)) {
            TextField(value = visitState.status.value,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorAccent),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.colorAccent),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        } else if (visitState.status.value == stringResource(R.string.objetive_weight)) {
            TextField(value = visitState.status.value,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorPrimary),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorPrimary),
                    unfocusedIndicatorColor = colorResource(R.color.colorPrimary),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        } else if (visitState.status.value == stringResource(R.string.aguda_moderada)) {
            TextField(value = visitState.status.value,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.orange),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.orange),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.orange),
                    unfocusedIndicatorColor = colorResource(R.color.orange),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.orange),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        } else {
            TextField(value = statusFormated,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.error),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.error),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.error),
                    unfocusedIndicatorColor = colorResource(R.color.error),
                ),
                onValueChange = {}, readOnly = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.error),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
        }

    }
    Spacer(modifier = Modifier.height(16.dp))

    TextField(value = visitState.observations.value,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.colorPrimary),
            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            cursorColor = colorResource(R.color.colorAccent),
            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            focusedIndicatorColor = colorResource(R.color.colorAccent),
            unfocusedIndicatorColor = colorResource(R.color.colorAccent),
        ),
        onValueChange = {visitState.observations.value = it},
        textStyle = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        leadingIcon = {
            Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
        label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) })



}


@Composable
fun SteptTitle(imageDrawable: Int, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(16.dp))
        Image(painterResource(id = imageDrawable), contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
    }
}


