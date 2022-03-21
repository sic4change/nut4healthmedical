package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun VisitsScreen(viewModel: VisitsViewModel = viewModel(), onClick: (Visit) -> Unit,
                onCreateVisitClick: (String) -> Unit) {
    val casesState = rememberVisitsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.visits != null) {
            casesState.visitsSize.value = viewModelState.visits!!.size
        }
    }


    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        //onCreateVisitClick(viewModelState.childId)
                    },
                    backgroundColor = colorResource(R.color.colorPrimary),
                    content = {
                        Icon(Icons.Filled.Add,"", tint = Color.White)
                    }
                )
            },
        ) {
            VisitItemsListScreen(
                loading = viewModelState.loading,
                items = viewModelState.visits,
                onClick = onClick
            )
        }

    }

}
