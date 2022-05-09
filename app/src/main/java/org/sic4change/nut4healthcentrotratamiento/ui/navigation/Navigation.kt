package org.sic4change.nut4healthcentrotratamiento.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import coil.annotation.ExperimentalCoilApi
import com.aaronat1.hackaton.ui.navigation.NavCommand
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CasesScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit.ChildItemEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.settings.SettingsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.*
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.VisitItemDetailScreen

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
                    NavCommand.ContentType(Feature.TUTORS).route
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
                onCreateTutorClick = { phone ->
                    navController.navigate(
                        NavCommand.ContentTypeCreate(Feature.CREATETUTOR).createRoute(phone)
                    )
                },
                onClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.TUTORS).createRoute(tutor.id)
                    )
                },
            )
        }

        composable(NavCommand.ContentTypeCreate(Feature.CREATETUTOR)) {
            TutorCreateScreen(onCreateTutor = {
                navController.navigate(
                    NavCommand.ContentType(Feature.TUTORS).route
                )
            })
        }

        /*composable(NavCommand.ContentType(Feature.CREATETUTOR)) {
            TutorCreateScreen(onCreateTutor = {
                navController.navigate(
                    NavCommand.ContentType(Feature.TUTORS).route
                )
            })
        }*/

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
                        NavCommand.ContentTypeDetail(Feature.EDITTUTOR).createRoute(tutor.id)
                    )
                },
                onChildClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILDS).createRoute(tutor.id)
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
                })
        }

        composable(NavCommand.ContentTypeDetail(Feature.EDITTUTOR)) {
            TutorEditScreen(onEditTutor = {
                navController.navigate(
                    NavCommand.ContentType(Feature.TUTORS).route
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.CHILDS)) {
            ChildsScreen(onClick =  { child ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                )
            },
            onCreateChildClick = { tutorId ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CREATECHILD).createRoute(tutorId)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL)) {
            ChildDetailScreen(onEditChildClick = { child ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.EDITCHILD).createRoute(child.id)
                )
            },
            onCasesClick = { child ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CASES).createRoute(child.id)
                )

            },
            onDeleteChildClick = { tutorId ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CHILDS).createRoute(tutorId)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.CREATECHILD)) {
            ChildCreateScreen( onCreateChild = { tutorId ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CHILDS).createRoute(tutorId)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.EDITCHILD)) {
            ChildEditScreen( onEditChild = { id ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(id)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.CASES)) {
            CasesScreen(onClick =  { case ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.VISITS).createRoute(case.id)
                )
            },
                onCreateCaseClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATECASE).createRoute(child)
                    )
            },
            onGoToDetailClick = { case ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(case.id)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.CREATECASE)) {
            CaseCreateScreen( onCreateCase = { childId->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CASES).createRoute(childId)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.CASE_DETAIL)) {
            CaseDetailScreen(onEditCaseClick = { case ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.EDITCASE).createRoute(case.id)
                )
            },
                onVisitsClick = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.VISITS).createRoute(case.id)
                    )
                },
                onDeleteCaseClick = { childId ->
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASES).createRoute(childId)
                    )
                })
        }

        composable(NavCommand.ContentTypeDetail(Feature.EDITCASE)) {
            CaseEditScreen( onEditCase = { id ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(id)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.VISITS)) {
            VisitsScreen(onClick =  { visit ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.VISIT_DETAIL).createRoute(visit.id)
                )

            },
                onCreateVisitClick = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATEVISIT).createRoute(case)
                    )
                }
            )

        }

        composable(NavCommand.ContentTypeDetail(Feature.VISIT_DETAIL)) {
            VisitDetailScreen(
                onEditVisitClick = { visit ->
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITVISIT).createRoute(visit.id)
                    )
            },
                onDeleteVisitClick = { caseId ->
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.VISITS).createRoute(caseId)
                    )
                },
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.CREATEVISIT)) {
            VisitCreateScreen(
                onCreateVisit = { caseId ->
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.VISITS).createRoute(caseId)
                    )
                },
                onChangeWeightOrHeight = { height, weight ->

                },
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.EDITVISIT)) {
            VisitEditScreen( onEditVisit = { id ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.VISIT_DETAIL).createRoute(id)
                )
            },
                onChangeWeightOrHeight = { height, weight ->

                },
            )
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