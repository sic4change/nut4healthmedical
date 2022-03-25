package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Symtom
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Treatment
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
    measlesVaccinated: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    vitamineAVaccinated: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    symtoms: MutableState<List<Symtom>> = rememberSaveable {mutableStateOf(listOf<Symtom>())},
    symtomsSelected: MutableState<List<Boolean>> = rememberSaveable { mutableStateOf(listOf<Boolean>()) },
    treatments: MutableState<List<Treatment>> = rememberSaveable {mutableStateOf(listOf<Treatment>())},
    treatmentsSelected: MutableState<List<Boolean>> = rememberSaveable { mutableStateOf(listOf<Boolean>()) },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    visitsSize: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    deleteVisit: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ VisitState(id, caseId, childId, tutorId, height, weight, imc, armCircunference,
    status, measlesVaccinated, vitamineAVaccinated, observations, symtoms, symtomsSelected,
    treatments, treatmentsSelected, createdDate, visitsSize, deleteVisit ) }

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
    val measlesVaccinated: MutableState<Boolean>,
    val vitamineAVaccinated: MutableState<Boolean>,
    val observations: MutableState<String>,
    val symtoms: MutableState<List<Symtom>>,
    val symtomsSelected: MutableState<List<Boolean>>,
    val treatments: MutableState<List<Treatment>>,
    val treatmentsSelected: MutableState<List<Boolean>>,
    val createdDate: MutableState<Date>,
    val visitsSize: MutableState<Int>,
    val deleteVisit: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteVisit.value = !deleteVisit.value
    }

}
