package org.sic4change.nut4healthcentrotratamiento.ui.screens.references

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
fun ReferencesScreen(
    onItemClick: (Derivation) -> Unit,
    viewModel: ReferencesViewModel = viewModel()) {
    val referencesState = rememberReferencesState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.references) {
        if (viewModelState.references != null) {
            referencesState.referencesSize.value = viewModelState.references!!.size
            referencesState.references.value = viewModelState.references
        }
    }


    NUT4HealthScreen {

        ReferencesItemsListScreen(
                loading = viewModelState.loading,
                items = referencesState.references.value,
                onClick = onItemClick,
                onSearch = viewModel::searchReference,
            )
        }

    }


