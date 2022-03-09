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
    name: MutableState<String> = rememberSaveable { mutableStateOf("") },
    surnames: MutableState<String> = rememberSaveable { mutableStateOf("") },
    address: MutableState<String> = rememberSaveable { mutableStateOf("") },
    phone: MutableState<String> = rememberSaveable { mutableStateOf("") },
    birthday: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    sex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    etnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    pregnant: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedSex: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionSex: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEtnician: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionEtnician: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedPregnant: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionPregnant: MutableState<String> = rememberSaveable { mutableStateOf("") },

) = remember{ TutorState(
    id, name, surnames, address, phone, birthday, sex, etnician, pregnant, observations,
    expandedSex, selectedOptionSex, expandedEtnician, selectedOptionEtnician,
    expandedPregnant, selectedOptionPregnant) }

class TutorState(
    val id: MutableState<String>,
    val name: MutableState<String>,
    val surnames: MutableState<String>,
    val address: MutableState<String>,
    val phone: MutableState<String>,
    val birthday: MutableState<Date>,
    val sex: MutableState<String>,
    val etnician: MutableState<String>,
    val pregnant: MutableState<String>,
    val observations: MutableState<String>,
    val expandedSex: MutableState<Boolean>,
    val selectedOptionSex: MutableState<String>,
    val expandedEtnician: MutableState<Boolean>,
    val selectedOptionEtnician: MutableState<String>,
    val expandedPregnant: MutableState<Boolean>,
    val selectedOptionPregnant: MutableState<String>,
) {



}
