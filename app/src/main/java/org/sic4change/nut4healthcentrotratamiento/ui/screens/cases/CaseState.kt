package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.*


@Composable
fun rememberCasesState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    tutorId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    state: MutableState<String> = rememberSaveable { mutableStateOf("") },
    visits: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    casesSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    createdCase:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteCase:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ CaseState(id, childId, tutorId, state, visits, observations,
    casesSize, createdCase, deleteCase) }

class CaseState(
    val id: MutableState<String>,
    val childId: MutableState<String>,
    val tutorId: MutableState<String>,
    val state: MutableState<String>,
    val visits: MutableState<String>,
    val observations: MutableState<String>,
    val casesSize: MutableState<Int>,
    val createdCase: MutableState<Boolean>,
    val deleteCase: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteCase.value = !deleteCase.value
    }

}
