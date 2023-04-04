package org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import java.util.*


@Composable
fun rememberCuadrantsState(
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
    cuadrants:  MutableState<List<Cuadrant?>> = rememberSaveable { mutableStateOf(emptyList()) },
    cuadrantsSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    filterText: MutableState<String> = rememberSaveable { mutableStateOf("") },
) = remember{ CuadrantState(id, childId, name, status, visits, lastDate, createdDate, observations,
    cuadrants, cuadrantsSize, filterText) }

class CuadrantState(
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
    val filterText: MutableState<String>
) {



}
