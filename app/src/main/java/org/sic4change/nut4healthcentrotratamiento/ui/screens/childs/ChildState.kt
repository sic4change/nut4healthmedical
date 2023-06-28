package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.*


@Composable
fun rememberChildsState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDetail: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    tutorId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    name: MutableState<String> = rememberSaveable { mutableStateOf("") },
    surnames: MutableState<String> = rememberSaveable { mutableStateOf("") },
    birthday: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    brothers: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    expandedBrothers: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionBrothers: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    lastDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    sex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    etnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedSex: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionSex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEtnician: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionEtnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    createdChild:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteChild:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showDateDialog:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    disabledView:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ ChildState(id, expandedDetail, tutorId, name, surnames, birthday, brothers, selectedOptionBrothers,
    expandedBrothers, lastDate, createdDate, sex, etnician, observations, expandedSex, selectedOptionSex,
    expandedEtnician, selectedOptionEtnician, childsSize, createdChild, deleteChild, showDateDialog,
    disabledView) }

class ChildState(
    val id: MutableState<String>,
    val expandedDetail: MutableState<Boolean>,
    val tutorId: MutableState<String>,
    val name: MutableState<String>,
    val surnames: MutableState<String>,
    val birthday: MutableState<Date>,
    val brothers: MutableState<Int>,
    val selectedOptionBrothers: MutableState<Int>,
    val expandedBrothers: MutableState<Boolean>,
    val lastDate: MutableState<Date>,
    val createdDate: MutableState<Date>,
    val sex: MutableState<String>,
    val etnician: MutableState<String>,
    val observations: MutableState<String>,
    val expandedSex: MutableState<Boolean>,
    val selectedOptionSex: MutableState<String>,
    val expandedEtnician: MutableState<Boolean>,
    val selectedOptionEtnician: MutableState<String>,
    val childsSize: MutableState<Int>,
    val createdChild: MutableState<Boolean>,
    val deleteChild: MutableState<Boolean>,
    val showDateDialog: MutableState<Boolean>,
    val disabledView: MutableState<Boolean>,
) {

    fun expandContractDetail() {
        expandedDetail.value = !expandedDetail.value
    }

    fun enableDisableView() {
        disabledView.value = !disabledView.value
    }

    fun showDeleteQuestion() {
        deleteChild.value = !deleteChild.value
    }


}
