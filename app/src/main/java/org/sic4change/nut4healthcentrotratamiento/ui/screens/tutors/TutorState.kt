package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.*


@Composable
fun rememberTutorState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    name: MutableState<String> = rememberSaveable { mutableStateOf("") },
    surnames: MutableState<String> = rememberSaveable { mutableStateOf("") },
    address: MutableState<String> = rememberSaveable { mutableStateOf("") },
    phone: MutableState<String> = rememberSaveable { mutableStateOf("") },
    birthday: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    lastDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    sex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    maleRelation: MutableState<String> = rememberSaveable { mutableStateOf("") },
    etnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    pregnant: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childMinor: MutableState<String> = rememberSaveable { mutableStateOf("") },
    weeks: MutableState<String> = rememberSaveable { mutableStateOf("0") },
    height: MutableState<String> = rememberSaveable { mutableStateOf("") },
    weight: MutableState<String> = rememberSaveable { mutableStateOf("") },
    imc: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedSex: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    expandedRelation: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionSex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    selectedOptionMaleRelations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEtnician: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionEtnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedPregnant: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    expandedChildMinor: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionPregnant: MutableState<String> = rememberSaveable { mutableStateOf("") },
    selectedOptionChildMinor: MutableState<String> = rememberSaveable { mutableStateOf("") },
    createdTutor:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteTutor:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },


) = remember{ TutorState(
    id, name, surnames, address, phone, birthday, lastDate, createdDate, sex, maleRelation, etnician, pregnant,
    childMinor, weeks, height, weight, imc, status, observations, expandedSex, expandedRelation, selectedOptionSex,
    selectedOptionMaleRelations, expandedEtnician, selectedOptionEtnician, expandedPregnant, expandedChildMinor,
    selectedOptionPregnant, selectedOptionChildMinor, createdTutor, deleteTutor
) }

class TutorState(
    val id: MutableState<String>,
    val name: MutableState<String>,
    val surnames: MutableState<String>,
    val address: MutableState<String>,
    val phone: MutableState<String>,
    val birthday: MutableState<Date>,
    val lastDate: MutableState<Date>,
    val createdDate: MutableState<Date>,
    val sex: MutableState<String>,
    val maleRelation: MutableState<String>,
    val etnician: MutableState<String>,
    val pregnant: MutableState<String>,
    val childMinor: MutableState<String>,
    val weeks: MutableState<String>,
    val height: MutableState<String>,
    val weight: MutableState<String>,
    val imc: MutableState<Double>,
    val status: MutableState<String>,
    val observations: MutableState<String>,
    val expandedSex: MutableState<Boolean>,
    val expandedMaleRelation: MutableState<Boolean>,
    val selectedOptionSex: MutableState<String>,
    val selectedOptionMaleRelations: MutableState<String>,
    val expandedEtnician: MutableState<Boolean>,
    val selectedOptionEtnician: MutableState<String>,
    val expandedPregnant: MutableState<Boolean>,
    val expandedChildMinor: MutableState<Boolean>,
    val selectedOptionPregnant: MutableState<String>,
    val selectedOptionChildMinor: MutableState<String>,
    val createdTutor: MutableState<Boolean>,
    val deleteTutor: MutableState<Boolean>,

    ) {

    fun showDeleteQuestion() {
        deleteTutor.value = !deleteTutor.value
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

    fun clearWomanValues() {
        childMinor.value = ""
        selectedOptionChildMinor.value = ""
        pregnant.value = ""
        selectedOptionPregnant.value = ""
        weeks.value = "0"
        height.value = ""
        weight.value = ""
        status.value = ""
    }

    fun clearManValues() {
        maleRelation.value = ""
        selectedOptionMaleRelations.value = ""
    }

}
