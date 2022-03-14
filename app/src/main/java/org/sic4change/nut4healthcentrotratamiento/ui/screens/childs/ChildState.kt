package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun rememberChildsState(
    childsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    createdChild:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    deleteChild:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ TutorState(childsSize, createdChild, deleteChild) }

class TutorState(
    val childsSize: MutableState<Int>,
    val createdChild: MutableState<Boolean>,
    val deleteChild: MutableState<Boolean>,
) {

    fun showDeleteQuestion() {
        deleteChild.value = !deleteChild.value
    }

}
