package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import java.util.*


@Composable
fun rememberCasesState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    name: MutableState<String> = rememberSaveable { mutableStateOf("") },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedStatus: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionStatus: MutableState<String> = rememberSaveable { mutableStateOf("") },
    visits: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    lastDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    cases:  MutableState<List<Case>> = rememberSaveable { mutableStateOf(emptyList()) },
    casesSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    createdCase:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteCase:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ CaseState(id, childId, name, status, expandedStatus, selectedOptionStatus,
    visits, lastDate, createdDate, observations, cases, casesSize, createdCase, deleteCase) }

class CaseState(
    val id: MutableState<String>,
    val childId: MutableState<String>,
    val name: MutableState<String>,
    val status: MutableState<String>,
    val expandedStatus: MutableState<Boolean>,
    val selectedOptionStatus: MutableState<String>,
    val visits: MutableState<String>,
    val lastDate: MutableState<Date>,
    val createdDate: MutableState<Date>,
    val observations: MutableState<String>,
    val cases: MutableState<List<Case>>,
    val casesSize: MutableState<Int>,
    val createdCase: MutableState<Boolean>,
    val deleteCase: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteCase.value = !deleteCase.value
    }

}
