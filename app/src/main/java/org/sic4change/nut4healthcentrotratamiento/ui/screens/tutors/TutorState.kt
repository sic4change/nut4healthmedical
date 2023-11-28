package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.R
import java.util.*


@Composable
fun rememberTutorState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDetail: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    name: MutableState<String> = rememberSaveable { mutableStateOf("") },
    surnames: MutableState<String> = rememberSaveable { mutableStateOf("") },
    address: MutableState<String> = rememberSaveable { mutableStateOf("") },
    phone: MutableState<String> = rememberSaveable { mutableStateOf("") },
    birthday: MutableState<Date?> = rememberSaveable { mutableStateOf(null) },
    lastDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    etnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEtnician: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionEtnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    sex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedSex: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionSex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    maleRelation: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedMaleRelation: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionMaleRelations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    womanStatus: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedWomanStatus: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionWomanStatus: MutableState<String> = rememberSaveable { mutableStateOf("") },
    weeks: MutableState<String> = rememberSaveable { mutableStateOf("0") },
    childMinor: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedChildMinor: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionChildMinor: MutableState<String> = rememberSaveable { mutableStateOf("") },
    babyAge: MutableState<String> = rememberSaveable { mutableStateOf("0") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    createdTutor:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteTutor:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    showDateDialog:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteChild:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    childId:  MutableState<String> = rememberSaveable { mutableStateOf("") },
    deleteCase:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    caseId:  MutableState<String> = rememberSaveable { mutableStateOf("") },
) = remember{ TutorState(
    id, expandedDetail, name, surnames, address, phone, birthday, lastDate, createdDate, etnician, expandedEtnician,
    selectedOptionEtnician, sex, expandedSex, selectedOptionSex, maleRelation, expandedMaleRelation,
    selectedOptionMaleRelations, womanStatus, expandedWomanStatus, selectedOptionWomanStatus,
    weeks, childMinor, expandedChildMinor, selectedOptionChildMinor, babyAge,
    observations, createdTutor, deleteTutor, showDateDialog, deleteChild, childId, deleteCase, caseId
) }

class TutorState(
    val id: MutableState<String>,
    val expandedDetail: MutableState<Boolean>,
    val name: MutableState<String>,
    val surnames: MutableState<String>,
    val address: MutableState<String>,
    val phone: MutableState<String>,
    val birthday: MutableState<Date?>,
    val lastDate: MutableState<Date>,
    val createdDate: MutableState<Date>,
    val etnician: MutableState<String>,
    val expandedEtnician: MutableState<Boolean>,
    val selectedOptionEtnician: MutableState<String>,
    val sex: MutableState<String>,
    val expandedSex: MutableState<Boolean>,
    val selectedOptionSex: MutableState<String>,
    val maleRelation: MutableState<String>,
    val expandedMaleRelation: MutableState<Boolean>,
    val selectedOptionMaleRelations: MutableState<String>,
    val womanStatus: MutableState<String>,
    val expandedWomanStatus: MutableState<Boolean>,
    val selectedOptionWomanStatus: MutableState<String>,
    val weeks: MutableState<String>,
    val childMinor: MutableState<String>,
    val expandedChildMinor: MutableState<Boolean>,
    val selectedOptionChildMinor: MutableState<String>,
    val babyAge: MutableState<String>,
    val observations: MutableState<String>,
    val createdTutor: MutableState<Boolean>,
    val deleteTutor: MutableState<Boolean>,
    val showDateDialog: MutableState<Boolean>,
    val deleteChild: MutableState<Boolean>,
    val childId: MutableState<String>,
    val deleteCase: MutableState<Boolean>,
    val caseId: MutableState<String>,) {

    fun expandContractDetail() {
        expandedDetail.value = !expandedDetail.value
    }

    fun showDeleteQuestion() {
        deleteTutor.value = !deleteTutor.value
    }

    fun showDeleteQuestion(id: String) {
        this.id.value = id
        deleteTutor.value = !deleteTutor.value
    }

    fun showDeleteChildQuestion() {
        deleteChild.value = !deleteChild.value
    }

    fun showDeleteChildQuestion(id: String) {
        this.childId.value = id
        deleteChild.value = !deleteChild.value
    }

    fun showDeleteCaseQuestion() {
        deleteCase.value = !deleteCase.value
    }

    fun showDeleteCaseQuestion(id: String) {
        this.caseId.value = id
        deleteCase.value = !deleteCase.value
    }

    fun clearWomanValues() {
        womanStatus.value = ""
        expandedWomanStatus.value = false
        selectedOptionWomanStatus.value = ""
        weeks.value = "0"
        childMinor.value = ""
        expandedChildMinor.value = false
        selectedOptionChildMinor.value = ""
        babyAge.value = "0"
    }

    fun clearWomanPregnantStatusValue() {
        babyAge.value = "0"
    }

    fun clearWomanInfantStatusValue() {
        childMinor.value = ""
        expandedChildMinor.value = false
        selectedOptionChildMinor.value = ""
        weeks.value = "0"
    }

    fun clearWomanPregnantAndInfantStatusValue() {
        childMinor.value = ""
        expandedChildMinor.value = false
        selectedOptionChildMinor.value = ""
    }

    fun clearWomanOtherStatusValue() {
        weeks.value = "0"
        childMinor.value = ""
        selectedOptionChildMinor.value = ""
        expandedChildMinor.value = false
        selectedOptionChildMinor.value = ""
        babyAge.value = "0"
    }

    fun clearManValues() {
        maleRelation.value = ""
        selectedOptionMaleRelations.value = ""
    }

}
