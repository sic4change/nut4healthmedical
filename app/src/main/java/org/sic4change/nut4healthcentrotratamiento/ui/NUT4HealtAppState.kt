package org.sic4change.nut4healthcentrotratamiento.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavCommand
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.Feature
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.navigatePopingUpToStartDestination

@Composable
fun rememberNUT4HealthAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) : NUT4HealthAppState = remember(scaffoldState, navController, coroutineScope) {
    NUT4HealthAppState(scaffoldState, navController, coroutineScope)

}

class NUT4HealthAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    companion object {
        val DRAWER_OPTIONS = listOf(NavItem.NEXT_VISITS, NavItem.TUTORS, NavItem.CUADRANTE, NavItem.SETTINGS)
        val BOTTOM_NAV_OPTIONS = listOf(NavItem.NEXT_VISITS, NavItem.TUTORS, NavItem.CUADRANTE, NavItem.SETTINGS)
    }

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

   /* val showUpNavigation: Boolean
        @Composable get() = currentRoute !in NavItem.values().map {
            it.navCommand.route
        }

    val showBottomNavigation: Boolean
        @Composable get() = BOTTOM_NAV_OPTIONS.any { currentRoute.contains(it.navCommand.feature.route) }*/

    val drawerSelectedIndex: Int
        /*@Composable get() =  if (showBottomNavigation) {
            DRAWER_OPTIONS.indexOf(NavItem.HOME)
        } else {
            DRAWER_OPTIONS.indexOfFirst { it.navCommand.route == currentRoute }
        }*/
        @Composable get() =
            DRAWER_OPTIONS.indexOfFirst { it.navCommand.route == currentRoute }

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onHomeClick() {
        navController.navigate(
            NavCommand.ContentType(Feature.NEXT_VISITS).route
        )
    }

    fun onMenuClick() {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }

    fun onNavItemClick(navItem: NavItem) {
        navController.navigatePopingUpToStartDestination(navItem.navCommand.route)

    }

    fun onDrawerOptionClick(navItem: NavItem) {
        coroutineScope.launch { scaffoldState.drawerState.close() }
        onNavItemClick(navItem)
    }



}