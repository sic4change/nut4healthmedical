package org.sic4change.nut4healthcentrotratamiento.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import coil.annotation.ExperimentalCoilApi
import com.aaronat1.hackaton.ui.navigation.NavCommand
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.settings.SettingsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorsScreen

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Feature.LOGIN.route
    ) {
        loginNav(navController)
        mainNav(navController)

        composable(NavCommand.ContentType(Feature.SETTINGS)) {
            SettingsScreen(onLogout = {
                navController.navigate(
                    NavCommand.ContentType(Feature.LOGIN).route
                )
            })
        }

        composable(NavCommand.ContentType(Feature.TUTORS)) {
            TutorsScreen ()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.loginNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.LOGIN).route,
        route = Feature.LOGIN.route
    ) {
        composable(NavCommand.ContentType(Feature.LOGIN)) {
            LoginScreen(onLogin = {
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.LOGIN).createRoute(0)
                )
                }
            )
        }
        composable(NavCommand.ContentTypeDetail(Feature.LOGIN)) {
            MainScreen(onLogout = {
                navController.navigate(
                    NavCommand.ContentType(Feature.LOGIN).route
                )
            })
        }

    }

}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.mainNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.HOME).route,
        route = Feature.HOME.route
    ) {
        composable(NavCommand.ContentTypeDetail(Feature.HOME)) {
            MainScreen(onLogout = {
                navController.navigate(
                    NavCommand.ContentType(Feature.HOME).route
                )
            })
        }

    }

}

private fun NavGraphBuilder.composable(
    navItem: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}