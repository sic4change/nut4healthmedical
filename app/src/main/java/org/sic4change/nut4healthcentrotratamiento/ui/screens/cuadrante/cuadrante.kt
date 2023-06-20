package org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante


import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CuadrantsScreen(
    onItemClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    viewModel: CuadrantsViewModel = viewModel()) {
    val cuadrantsState = rememberCuadrantsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.cuadrants) {
        if (viewModelState.cuadrants != null) {
            cuadrantsState.casesSize.value = viewModelState.cuadrants!!.size
            cuadrantsState.cases.value = viewModelState.cuadrants.filterNotNull().filter { it!!.visits != "0" }
        }
    }


    NUT4HealthScreen {

        Scaffold(

        ) {
            CuadrantItemsListScreen(
                loading = viewModelState.loading,
                items = cuadrantsState.cases.value,
                onClick = onItemClick,
                onCreateVisitClick = onCreateVisitClick,
                onSearch = viewModel::searchTutor
            )
        }

    }

}

