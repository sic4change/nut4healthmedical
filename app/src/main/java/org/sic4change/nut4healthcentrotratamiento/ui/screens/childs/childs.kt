package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun ChildsScreen(viewModel: ChildsViewModel = viewModel(), onClick: (Child) -> Unit,
                 onCreateChildClick: () -> Unit) {
    val childsState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.childs) {
        if (viewModelState.childs != null) {
            childsState.childsSize.value = viewModelState.childs!!.size
        }
    }


    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onCreateChildClick()
                    },
                    backgroundColor = colorResource(R.color.colorPrimary),
                    content = {
                        Icon(Icons.Filled.Add,"", tint = Color.White)
                    }
                )
            },
        ) {
            ChildItemsListScreen(
                loading = viewModelState.loading,
                items = viewModelState.childs,
                onClick = onClick
            )
        }

    }

}

