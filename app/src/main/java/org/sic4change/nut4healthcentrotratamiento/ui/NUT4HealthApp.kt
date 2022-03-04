package org.sic4change.nut4healthcentrotratamiento.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.AppBarIcon
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.Navigation
import org.sic4change.nut4healthcentrotratamiento.ui.theme.NUT4HealthTheme

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NUT4HealthApp() {
    val appState =  rememberNUT4HealthAppState()

    NUT4HealthScreen {
        Scaffold (
            /*topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = { AppBarIcon(Icons.Default.AccountCircle, {}) },
                )
            },*/
            scaffoldState = appState.scaffoldState
        ) {
            Navigation(appState.navController)
        }
    }
}

@Composable
fun NUT4HealthScreen(content: @Composable () -> Unit) {
    NUT4HealthTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            content = content
        )
    }
}