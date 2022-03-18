package org.sic4change.nut4healthcentrotratamiento.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberMainState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    username: MutableState<String> = rememberSaveable { mutableStateOf("") },
    email: MutableState<String> = rememberSaveable { mutableStateOf("") },
    role: MutableState<String> = rememberSaveable { mutableStateOf("") },
    logout: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    phoneToCheck: MutableState<String> = rememberSaveable { mutableStateOf("") },
    openDialog: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    editPhoneToCheck: MutableState<String> = rememberSaveable { mutableStateOf("") },
) = remember{ MainState(
    id, username, email, role, logout, phoneToCheck, openDialog, editPhoneToCheck) }

class MainState(
    val id: MutableState<String>,
    val username: MutableState<String>,
    val email: MutableState<String>,
    val role: MutableState<String>,
    val logout: MutableState<Boolean>,
    val phoneToCheck: MutableState<String>,
    val openDialog: MutableState<Boolean>,
    val editPhoneToCheck: MutableState<String>,
) {

    fun showLogoutQuestion() {
        logout.value = !logout.value
    }


}
