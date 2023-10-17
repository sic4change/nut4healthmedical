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
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante.CuadrantsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts.NextScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.settings.SettingsScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.*
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitDetailScreen

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
                    NavCommand.ContentType(Feature.NEXT_VISITS).route
                )
                }
            )
        }
        composable(NavCommand.ContentTypeDetail(Feature.LOGIN)) {
            MainScreen(onNotificationChildClick = {
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

        composable(
            NavCommand.ContentTypeDetail(Feature.HOME)
        ) {
            MainScreen(onNotificationChildClick =  {})
        }

        composable(NavCommand.ContentType(Feature.SETTINGS)) {
            SettingsScreen(onLogout = {
                navController.navigate(
                    NavCommand.ContentType(Feature.LOGIN).route
                )
            })
        }

        composable(NavCommand.ContentType(Feature.NEXT_VISITS)) {
            NextScreen(
                onNotificationChildClick =  { childId ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(childId)
                    )
                },
                onItemClick = { cuadrant ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(cuadrant.visitsCuadrant[0].caseId)
                    )
                },
                onCreateVisitClick = { caseId ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATEVISIT).createRoute(caseId)
                    )
                },
                onClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.TUTORS).createRoute(tutor.id)
                    )
                },
                onCreateTutorClick = { phone ->
                    navController.navigate(
                        NavCommand.ContentTypeCreate(Feature.CREATETUTOR).createRoute(phone)
                    )
                },
                onLogout = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.LOGIN).route
                    )
                }
            )
        }

        composable(NavCommand.ContentType(Feature.CUADRANTE)) {
            CuadrantsScreen(
                onItemClick = { cuadrant ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(cuadrant.visitsCuadrant[0].caseId)
                    )
                },
                onCreateVisitClick = { caseId ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATEVISIT).createRoute(caseId)
                    )
                },
            )
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
                onClickDetail = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL).createRoute(tutor.id)
                    )
                },
                onClickEdit = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITTUTOR).createRoute(tutor.id)
                    )
                },
                onClickDelete = { tutor ->

                },
                onDeleteTutor = {
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                },
                onLogout = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.LOGIN).route
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeCreate(Feature.CREATETUTOR)) {
            TutorCreateScreen(onCreateTutor = { tutor ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL).createRoute(tutor)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.FEFA)) {
            FEFADetailScreen(
                onEditTutorClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITTUTOR).createRoute(tutor.id)
                    )
                },
                onDeleteTutorClick = {

                },
                onTutorDeleted = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                },
                onCaseCreated = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATEVISIT).createRoute(case.id)
                    )
                },
                onItemClick = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(case.id)
                    )
                },
                onClickDetail = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(case.id)
                    )
                },
                onClickEdit = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITCASE).createRoute(case.id)
                    )
                },
                onClickDelete = { case ->

                },
                onDeleteCase = { caseId ->
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.FEFA).createRoute(caseId)
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
                onDeleteTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                },
                onItemClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                    )
                },
                onCreateChildClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATECHILD).createRoute(tutor.id)
                    )
                },
                onClickDetail = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                    )
                },
                onClickEdit = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITCHILD).createRoute(child.id)
                    )
                },
                onClickDelete = { child ->

                },
                onDeleteChild = {tutorId ->
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL).createRoute(tutorId)
                    )
                },
                onFEFAClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.FEFA).createRoute(child.id)
                    )
                },
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.EDITTUTOR)) {
            TutorEditScreen(onEditTutor = { tutor ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL).createRoute(tutor)
                )
            })
        }

        composable(NavCommand.ContentTypeDetail(Feature.TUTORS)) {
            TutorDetailScreen(
                onEditTutorClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITTUTOR).createRoute(tutor.id)
                    )
                },
                onCreateChildClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATECHILD).createRoute(tutor.id)
                    )
                },
                onItemClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                    )
                },
                onDeleteTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                },
                onClickDetail = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                    )
                },
                onClickEdit = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITCHILD).createRoute(child.id)
                    )
                },
                onClickDelete = { child ->

                },
                onDeleteChild = {

                },
                onFEFAClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.FEFA).createRoute(child.id)
                    )
                },
            )
        }


        composable(NavCommand.ContentTypeDetail(Feature.HOME)) {
            TutorDetailScreen(
                onEditTutorClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITTUTOR).createRoute(tutor.id)
                    )
                },
                onCreateChildClick = { tutor ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATECHILD).createRoute(tutor.id)
                    )
                },
                onItemClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                    )
                },
                onDeleteTutorClick = {
                    navController.navigate(
                        NavCommand.ContentType(Feature.TUTORS).route
                    )
                },
                onClickDetail = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(child.id)
                    )
                },
                onClickEdit = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITCHILD).createRoute(child.id)
                    )
                },
                onClickDelete = { child ->

                },
                onDeleteChild = {

                },
                onFEFAClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.FEFA).createRoute(child.id)
                    )
                },
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL)) {
            ChildDetailScreen(
                onEditChildClick = { child ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITCHILD).createRoute(child.id)
                    )
                },
                onDeleteChildClick = { tutorId ->
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL).createRoute(tutorId)
                    )
                },
                onGoToUniqueCase = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(case.id)
                    )
                },
                onItemClick = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(case.id)
                    )
                },
                onCaseCreated = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATEVISIT).createRoute(case.id)
                    )
                },
                onClickDetail = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(case.id)
                    )
                },
                onClickEdit = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EDITCASE).createRoute(case.id)
                    )
                },
                onClickDelete = { case ->

                },
                onDeleteCase = { caseId ->
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(caseId)
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.CREATECHILD)) {
            ChildCreateScreen( onCreateChild = { tutorId ->
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.TUTORS_DETAIL).createRoute(tutorId)
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

        composable(NavCommand.ContentTypeDetail(Feature.CASE_DETAIL)) {
            CaseDetailScreen(
                onEditCaseClick = { case ->
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.EDITCASE).createRoute(case.id)
                )
                },
                onCreateVisitClick = { case ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CREATEVISIT).createRoute(case.id)
                    )
                },
                onItemClick = { visit ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.VISIT_DETAIL).createRoute(visit.id)
                    )
                },
                onDeleteCaseClick = { childId ->
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(childId)
                    )
                })
        }

        composable(NavCommand.ContentTypeDetail(Feature.EDITCASE)) {
            CaseEditScreen( onEditCase = { id ->
                navController.popBackStack()
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(
                    NavCommand.ContentTypeDetail(Feature.CHILD_DETAIL).createRoute(id)
                )
            })
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
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(caseId)
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
                        NavCommand.ContentTypeDetail(Feature.CASE_DETAIL).createRoute(caseId)
                    )
                },
                onChangeWeightOrHeight = { height, weight ->

                },
                onCancelCreateVisit = {
                    navController.popBackStack()
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
        arguments = navItem.args,
    ) {
        content(it)
    }
}