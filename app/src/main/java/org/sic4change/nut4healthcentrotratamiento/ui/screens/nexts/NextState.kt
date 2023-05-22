package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import java.util.*


@Composable
fun rememberNextState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    name: MutableState<String> = rememberSaveable { mutableStateOf("") },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    visits: MutableState<String> = rememberSaveable { mutableStateOf("") },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    lastDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    cuadrants:  MutableState<List<Cuadrant?>> = rememberSaveable { mutableStateOf(emptyList()) },
    cuadrantsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    filterValue: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    phoneToCheck: MutableState<String> = rememberSaveable { mutableStateOf("") },
    openDialogSearchByPhone: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    editPhoneToCheck: MutableState<String> = rememberSaveable { mutableStateOf("") },
    point: MutableState<String> = rememberSaveable { mutableStateOf("") },
    phoneCode: MutableState<String> = rememberSaveable { mutableStateOf("") },
    phoneLength: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    pointId: MutableState<String> = rememberSaveable { mutableStateOf("") },
) = remember{ NextState(id, childId, name, status, visits, lastDate, createdDate, observations,
    cuadrants, cuadrantsSize, filterValue, phoneToCheck, openDialogSearchByPhone,
    editPhoneToCheck,point, phoneCode, phoneLength, pointId,) }

class NextState(
    val id: MutableState<String>,
    val childId: MutableState<String>,
    val name: MutableState<String>,
    val status: MutableState<String>,
    val visits: MutableState<String>,
    val lastDate: MutableState<Date>,
    val createdDate: MutableState<Date>,
    val observations: MutableState<String>,
    val cases: MutableState<List<Cuadrant?>>,
    val casesSize: MutableState<Int>,
    val filterValue: MutableState<Int>,
    val phoneToCheck: MutableState<String>,
    val openDialogSearchByPhone: MutableState<Boolean>,
    val editPhoneToCheck: MutableState<String>,
    val point: MutableState<String>,
    val phoneCode: MutableState<String>,
    val phoneLength: MutableState<Int>,
    val pointId: MutableState<String>,
) {



}
