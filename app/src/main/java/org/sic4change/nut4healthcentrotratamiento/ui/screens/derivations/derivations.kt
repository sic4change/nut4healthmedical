package org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi



@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DerivationCreateScreen(viewModel: DerivationCreateViewModel = viewModel(), onCreateDerivation: (String) -> Unit) {
    val derivationCreateState = rememberDerivationState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {

        }
    }

    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            derivationCreateState.child.value = viewModelState.child!!
        }
    }

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            derivationCreateState.pointId.value = viewModelState.point!!.id
            derivationCreateState.currentPointName.value = viewModelState.point!!.name
        }
    }

    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            derivationCreateState.currentPointPhone.value = viewModelState.user!!.phone
        }
    }

    LaunchedEffect(viewModelState.healthCentreUsers) {
        if (viewModelState.healthCentreUsers != null) {
            derivationCreateState.healthCentres.value = viewModelState.healthCentreUsers.toMutableList()
        }
    }

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            derivationCreateState.tutor.value = viewModelState.tutor!!
        }
    }

    LaunchedEffect(viewModelState.points) {
        if (viewModelState.points != null) {
            derivationCreateState.points.value = viewModelState.points.toMutableList()
        }
    }

    DerivationItemCreateScreen(
        loading = viewModelState.loading,
        derivationState = derivationCreateState,
        onCreateDerivation = viewModel::createDerivation
    )
}


