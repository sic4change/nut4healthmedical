package org.sic4change.nut4healthcentrotratamiento.ui.screens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.*

@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel(), onLogout: () -> Unit) {
    val mainState = rememberMainState()
    val viewModelState by viewModel.state.collectAsState()
    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            mainState.id.value = viewModelState.user!!.id
            mainState.role.value = viewModelState.user!!.role
            mainState.email.value = viewModelState.user!!.email
            mainState.username.value = viewModelState.user!!.username
        }
    }

    BackHandler {
        activity?.finish()
    }



    NUT4HealthScreen {


        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {

                Card {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {

                       Text("Bienvenido")



                    }
                }
            }

        }
    }



}

