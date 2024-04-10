package org.sic4change.nut4healthcentrotratamiento.ui.screens.references

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import java.util.*


@Composable
fun rememberReferencesState(
    references:  MutableState<List<Derivation?>> = rememberSaveable { mutableStateOf(emptyList()) },
    referencesSize:  MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    filterText: MutableState<String> = rememberSaveable { mutableStateOf("") },
) = remember{ ReferencesState(references, referencesSize, filterText) }

class ReferencesState(
    val references: MutableState<List<Derivation?>>,
    val referencesSize: MutableState<Int>,
    val filterText: MutableState<String>
) {



}
