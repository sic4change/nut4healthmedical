package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor


@Composable
fun rememberReferencesTransferencesState(
    referencesTransferences:  MutableState<List<Derivation?>> = rememberSaveable { mutableStateOf(emptyList()) },
    tutors:  MutableState<List<Tutor?>> = rememberSaveable { mutableStateOf(emptyList()) },
    childs:  MutableState<List<Child?>> = rememberSaveable { mutableStateOf(emptyList()) },
    filterText: MutableState<String> = rememberSaveable { mutableStateOf("") },
) = remember{ ReferencesTransferencesState(referencesTransferences, tutors, childs, filterText) }

class ReferencesTransferencesState(
    val referencesTransferences: MutableState<List<Derivation?>>,
    val tutors: MutableState<List<Tutor?>>,
    val childs: MutableState<List<Child?>>,
    val filterText: MutableState<String>
) {



}
