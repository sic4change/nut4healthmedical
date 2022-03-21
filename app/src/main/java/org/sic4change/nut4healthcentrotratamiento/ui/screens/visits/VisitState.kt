package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import java.util.*


@Composable
fun rememberVisitsState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    caseId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    height: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    weight: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    imc: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    armCircunference: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    visitsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    deleteVisit:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ VisitState(id, caseId, height, weight, imc, armCircunference, status, observations, createdDate,
    visitsSize, deleteVisit ) }

class VisitState(
    val id: MutableState<String>,
    val caseId: MutableState<String>,
    val height: MutableState<Int>,
    val weight: MutableState<Double>,
    val imc: MutableState<Double>,
    val armCircunference: MutableState<Double>,
    val status: MutableState<String>,
    val observations: MutableState<String>,
    val createdDate: MutableState<Date>,
    val visitsSize: MutableState<Int>,
    val deleteVisit: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteVisit.value = !deleteVisit.value
    }

}
