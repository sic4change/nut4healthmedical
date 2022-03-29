package org.sic4change.nut4healthcentrotratamiento.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.annotation.ExperimentalCoilApi
import com.aaronat1.hackaton.ui.navigation.NavCommand
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.AppBarIcon
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.DrawerContent
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.Feature
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.Navigation
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState
import org.sic4change.nut4healthcentrotratamiento.ui.theme.NUT4HealthTheme

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NUT4HealthApp() {
    val appState =  rememberNUT4HealthAppState()
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    NUT4HealthScreen {
        if (navBackStackEntry?.destination?.route !=  NavCommand.ContentType(Feature.LOGIN).route) {
            Scaffold (
                topBar = {
                    TopAppBar(
                        backgroundColor = colorResource(R.color.colorPrimary),
                        title = { Text(stringResource(R.string.app_name), color = colorResource(R.color.white)) },
                        navigationIcon = {
                            if (!appState.currentRoute.contains("login/detail") &&
                                !appState.currentRoute.contains("settings/home") &&
                                !appState.currentRoute.contains("tutors/home")    ) {
                                AppBarIcon(
                                    imageVector = Icons.Default.ArrowBack,
                                    onClick = { appState.onUpClick() })
                            } else {
                                AppBarIcon(
                                    imageVector = Icons.Default.Menu,
                                    onClick = { appState.onMenuClick()}
                                )
                            }

                        },

                        )
                },

                drawerContent = {
                    if (navBackStackEntry?.destination?.route !=  NavCommand.ContentType(Feature.LOGIN).route) {
                        DrawerContent(
                            drawerOptions = NUT4HealthAppState.DRAWER_OPTIONS,
                            selectedIndex = appState.drawerSelectedIndex,
                            onOptionClick = { navItem ->
                                appState.onDrawerOptionClick(navItem)
                            }
                        ) }
                },
                scaffoldState = appState.scaffoldState
            ) {
                Navigation(appState.navController)
            }
        } else {
            Scaffold (

                scaffoldState = appState.scaffoldState
            ) {
                Navigation(appState.navController)
            }
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