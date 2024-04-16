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
import org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations.DerivationCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations.rememberDerivationState

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
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ReferencesTransferencesCaseDetailScreen(viewModel: ReferencesTransferencesCaseDetailViewModel = viewModel(),
                                            onCrateVisit: (Derivation) -> Unit,) {

    val referencesTransferencesState = rememberReferencesTransferencesState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.derivation) {
        if (viewModelState.derivation != null) {
            referencesTransferencesState.derivation.value = viewModelState.derivation!!
        }
    }

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            referencesTransferencesState.case.value = viewModelState.case!!
        }
    }

    LaunchedEffect(viewModelState.lastVisit) {
        if (viewModelState.lastVisit != null) {
            referencesTransferencesState.lastVisit.value = viewModelState.lastVisit!!
        }
    }

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.lastVisit != null) {
            referencesTransferencesState.visits.value = viewModelState.visits!!
        }
    }

    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            referencesTransferencesState.child.value = viewModelState.child!!
        }
    }

    LaunchedEffect(viewModelState.fefa) {
        if (viewModelState.fefa != null) {
            referencesTransferencesState.fefa.value = viewModelState.fefa!!
        }
    }

    LaunchedEffect(viewModelState.origin) {
        if (viewModelState.origin != null) {
            referencesTransferencesState.origin.value = viewModelState.origin!!
        }
    }

    NUT4HealthScreen {
        ReferencesTransferencesCaseDetailView(
            referencesTransferencesState = referencesTransferencesState,
            derivation = referencesTransferencesState.derivation.value!!,
            child = referencesTransferencesState.child.value!!,
            tutor = referencesTransferencesState.fefa.value!!,
            case = referencesTransferencesState.case.value!!,
            lastVisit = referencesTransferencesState.lastVisit.value!!,
            visits = referencesTransferencesState.visits.value!!,
            origin = referencesTransferencesState.origin.value!!,
            loading = viewModelState.loading,
            onCreateVisit = onCrateVisit
        )
    }

}


