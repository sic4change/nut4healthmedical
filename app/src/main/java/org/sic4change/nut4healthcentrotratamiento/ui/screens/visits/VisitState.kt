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
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    visitsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
) = remember{ VisitState(id, caseId, status, createdDate, visitsSize ) }

class VisitState(
    val id: MutableState<String>,
    val caseId: MutableState<String>,
    val status: MutableState<String>,
    val createdDate: MutableState<Date>,
    val visitsSize: MutableState<Int>,
) {

    /*fun showDeleteQuestion() {
        deleteCase.value = !deleteCase.value
    }*/

}
