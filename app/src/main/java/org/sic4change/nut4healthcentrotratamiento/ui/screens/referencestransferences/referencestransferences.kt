package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun ReferenceScreen(
    onItemClick: (Derivation) -> Unit,
    viewModel: ReferencesTransferencesViewModel = viewModel()
) {
    val referencesTransferencesState = rememberReferencesTransferencesState()
    val viewModelState by viewModel.state.collectAsState()
    viewModelState.type = "Referred"

    LaunchedEffect(viewModelState.referencesTransferences) {
        if (viewModelState.referencesTransferences != null) {
            referencesTransferencesState.tutors.value = viewModelState.fefas
            referencesTransferencesState.childs.value = viewModelState.childs
            referencesTransferencesState.referencesTransferences.value = viewModelState.referencesTransferences
        }
    }

    LaunchedEffect(viewModelState.points) {
        if (viewModelState.points != null) {
            referencesTransferencesState.origins.value = viewModelState.points.map { it!!.name }
        }
    }


    NUT4HealthScreen {
        ReferencesTransferencesItemsListScreen(
            type = viewModelState.type,
            loading = viewModelState.loading,
            items = referencesTransferencesState.referencesTransferences.value,
            tutors = referencesTransferencesState.tutors.value,
            childs = referencesTransferencesState.childs.value,
            points = referencesTransferencesState.origins.value,
            onClick = onItemClick
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun TransferenceScreen(
    onItemClick: (Derivation) -> Unit,
    viewModel: ReferencesTransferencesViewModel = viewModel()
) {
    val referencesTransferencesState = rememberReferencesTransferencesState()
    val viewModelState by viewModel.state.collectAsState()
    viewModelState.type = "Transfered"

    LaunchedEffect(viewModelState.referencesTransferences) {
        if (viewModelState.referencesTransferences != null) {
            referencesTransferencesState.tutors.value = viewModelState.fefas
            referencesTransferencesState.childs.value = viewModelState.childs
            referencesTransferencesState.referencesTransferences.value = viewModelState.referencesTransferences
        }
    }


    LaunchedEffect(viewModelState.points) {
        if (viewModelState.points != null) {
            referencesTransferencesState.origins.value = viewModelState.points.map { it!!.name }
        }
    }


    NUT4HealthScreen {
        ReferencesTransferencesItemsListScreen(
            type = viewModelState.type,
            loading = viewModelState.loading,
            items = referencesTransferencesState.referencesTransferences.value,
            tutors = referencesTransferencesState.tutors.value,
            childs = referencesTransferencesState.childs.value,
            points = referencesTransferencesState.origins.value,
            onClick = onItemClick
        )
    }

}


