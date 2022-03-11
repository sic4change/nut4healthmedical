package org.sic4change.nut4healthcentrotratamiento.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.aaronat1.hackaton.ui.navigation.NavCommand
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.settings.SettingsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.*

@RequiresApi(Build.VERSION_CODES.O)
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
                    NavCommand.ContentTypeDetail(Feature.LOGIN).createRoute("0")
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

@RequiresApi(Build.VERSION_CODES.O)
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

        composable(NavCommand.ContentType(Feature.SETTINGS)) {
            SettingsScreen(onLogout = {
                navController.navigate(
                    NavCommand.ContentType(Feature.LOGIN).route
                )
            })
        }

        composable(NavCommand.ContentType(Feature.TUTORS)) {
            TutorsScreen(
                onCreateTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.CREATETUTOR).route
                    )
                },
                onClick = { tutor ->

                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.TUTORS).createRoute(tutor.id)
                    )
                },
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL)) {
            TutorDetailScreen(
                onEditTutorClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITTUTOR).createRoute(tutor.id)
                    )
                },
                onChildClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.CHILDS).route
                    )
                },
                onDeleteTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.TUTORS)) {
            TutorDetailScreen(
                onEditTutorClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentType(Feature.CREATETUTOR).route
                    )
                },
                onChildClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.CHILDS).route
                    )
                },
                onDeleteTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                })
        }

        composable(NavCommand.ContentTypeDetail(Feature.HOME)) {
            TutorDetailScreen(
                onEditTutorClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentType(Feature.CREATETUTOR).route
                    )
                },
                onChildClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.CHILDS).route
                    )
                },
                onDeleteTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                }
            )
        }

        composable(NavCommand.ContentType(Feature.CREATETUTOR)) {
            TutorCreateScreen(onCreateTutor = {
                navController.navigate(
                    NavCommand.ContentType(Feature.TUTORS).route
                )
            })
        }

        composable(NavCommand.ContentType(Feature.EDITTUTOR)) {
            TutorEditScreen()
        }

        composable(NavCommand.ContentType(Feature.CHILDS)) {
            ChildScreen()
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