package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun NextScreen(
    onItemClick: (Cuadrant) -> Unit,
    viewModel: NextsViewModel= viewModel()) {
    val nextState = rememberNextState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.cuadrants) {
        if (viewModelState.cuadrants != null) {
            nextState.casesSize.value = viewModelState.cuadrants!!.size
            nextState.cases.value = viewModelState.cuadrants
        }
    }


    NUT4HealthScreen {

        Scaffold {
            NextItemsListScreen(
                loading = viewModelState.loading,
                items = nextState.cases.value,
                onClick = onItemClick,
                onFilter = viewModel::filterNext
            )
        }

    }

}

