package com.aaronat1.hackaton.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.Feature
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.MainScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginScreen

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
        registerNav(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.registerNav(navController: NavController) {
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
            MainScreen()
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