package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
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
    armCircunference: MutableState<Double> = rememberSaveable { mutableStateOf(30.0) },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    selectedEdema: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEdema: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    measlesVaccinated: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    vitamineAVaccinated: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childDateMillis: MutableState<Long> = rememberSaveable { mutableStateOf(0) },
    treatments: MutableState<MutableList<Treatment>> = rememberSaveable {mutableStateOf(mutableListOf<Treatment>())},
    symtoms: MutableState<MutableList<Symtom>> = rememberSaveable {mutableStateOf(mutableListOf<Symtom>())},
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdVisit:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visitsSize: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    deleteVisit: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ VisitState(id, caseId, childId, tutorId, height, weight, imc, armCircunference,
    status, selectedEdema, expandedEdema, measlesVaccinated, vitamineAVaccinated, observations,
    childDateMillis, symtoms, treatments, createdDate, createdVisit,visitsSize, deleteVisit, ) }

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
    val selectedEdema: MutableState<String>,
    val expandedEdema: MutableState<Boolean>,
    val measlesVaccinated: MutableState<Boolean>,
    val vitamineAVaccinated: MutableState<Boolean>,
    val observations: MutableState<String>,
    val childDateMillis: MutableState<Long>,
    val symtoms: MutableState<MutableList<Symtom>>,
    val treatments: MutableState<MutableList<Treatment>>,
    val createdDate: MutableState<Date>,
    val createdVisit: MutableState<Boolean>,
    val visitsSize: MutableState<Int>,
    val deleteVisit: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteVisit.value = !deleteVisit.value
    }

    fun formatHeightValue(value: String) {
        val temp = value.replace(",", ".").
        replace(" ", "").replace("-", "")
        if (temp != "." && temp.filter { it == '.' }.count() < 2) {
            height.value = temp
        }
    }

    fun formatWeightValue(value: String) {
        val temp = value.replace(",", ".").
            replace(" ", "").replace("-", "")
        if (temp != "." && temp.filter { it == '.' }.count() < 2) {
            weight.value = temp
        }
    }

}
