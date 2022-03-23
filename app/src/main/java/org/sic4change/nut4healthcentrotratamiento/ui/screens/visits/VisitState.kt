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
    childId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    tutorId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    height: MutableState<String> = rememberSaveable { mutableStateOf("") },
    weight: MutableState<String> = rememberSaveable { mutableStateOf("") },
    imc: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    armCircunference: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    symtoms: MutableState<List<String>> = rememberSaveable {mutableStateOf(listOf<String>())},
    treatments: MutableState<List<String>> = rememberSaveable {mutableStateOf(listOf<String>())},
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    visitsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    deleteVisit:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ VisitState(id, caseId, childId, tutorId, height, weight, imc, armCircunference,
    status, observations, symtoms, treatments, createdDate,  visitsSize, deleteVisit ) }

class VisitState(
    val id: MutableState<String>,
    val caseId: MutableState<String>,
    val childId: MutableState<String>,
    val tutorId: MutableState<String>,
    val height: MutableState<String>,
    val weight: MutableState<String>,
    val imc: MutableState<Double>,
    val armCircunference: MutableState<Double>,
    val status: MutableState<String>,
    val observations: MutableState<String>,
    val symtoms: MutableState<List<String>>,
    val treatments: MutableState<List<String>>,
    val createdDate: MutableState<Date>,
    val visitsSize: MutableState<Int>,
    val deleteVisit: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteVisit.value = !deleteVisit.value
    }

}
