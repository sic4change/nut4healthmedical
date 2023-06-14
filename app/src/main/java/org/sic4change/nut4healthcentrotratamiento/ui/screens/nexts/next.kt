package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts

import android.app.Activity
import android.Manifest
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import org.sic4change.nut4healthcentrotratamiento.MainActivity
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.commons.MessageErrorRole
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.SearchByPhoneDialog

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class
)
@Composable
fun NextScreen(
    onItemClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    onClick: (Tutor) -> Unit,
    onCreateTutorClick: (String) -> Unit,
    onNotificationChildClick: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: NextsViewModel = viewModel()) {


    val nextState = rememberNextState()
    val viewModelState by viewModel.state.collectAsState()

    val activity = (LocalContext.current as? Activity)

    val permission: PermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            nextState.id.value = viewModelState.user!!.id
            nextState.role.value = viewModelState.user!!.role
            nextState.email.value = viewModelState.user!!.email
            nextState.username.value = viewModelState.user!!.username
            viewModel.getPoint(viewModelState.user!!.point)
            if (MainActivity.notificationChildId.isNotEmpty()) {
                onNotificationChildClick(MainActivity.notificationChildId)
            }
            if (nextState.role.value != "Servicio Salud") {
                nextState.showRoleError()
            } else {
                viewModel.subscribeToPointNotifications()
            }

            if (!permission.hasPermission) {
                permission.launchPermissionRequest()
            }

        }
    }

    LaunchedEffect(viewModelState.cuadrants) {
        if (viewModelState.cuadrants != null) {
            nextState.casesSize.value = viewModelState.cuadrants!!.size
            nextState.cases.value = viewModelState.cuadrants.filterNotNull().filter { it!!.visits != "0" }
        }
    }

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            if (viewModelState.tutorChecked == "found") {
                onClick(viewModelState.tutor!!)
                viewModel.resetTutor()
                nextState.phoneToCheck.value = ""
                nextState.editPhoneToCheck.value = ""
            } else if (viewModelState.tutorChecked == "not_found") {
                onCreateTutorClick(nextState.phoneCode.value + nextState.phoneToCheck.value)
                viewModel.resetTutor()
                nextState.phoneToCheck.value = ""
                nextState.editPhoneToCheck.value = ""
            }
        }
    }

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            nextState.pointId.value = viewModelState.point!!.id
            nextState.point.value = viewModelState.point!!.fullName
            nextState.phoneCode.value = viewModelState.point!!.phoneCode
            nextState.phoneLength.value = viewModelState.point!!.phoneLength
        }
    }

    BackHandler {
        activity?.finish()
    }

    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                Row {
                    ExtendedFloatingActionButton(
                        onClick = {
                            nextState.openDialogSearchByPhone.value = true
                        },
                        text = { Text(stringResource(R.string.new_tutor), color = colorResource(R.color.white)) },
                        backgroundColor = colorResource(R.color.colorPrimary),
                        icon = {
                            Icon(Icons.Filled.Add,"", tint = Color.White)
                        }
                    )

                }

            },
        ) {
            NextItemsListScreen(
                loading = viewModelState.loading,
                items = nextState.cases.value,
                onClick = onItemClick,
                onCreateVisitClick = onCreateVisitClick,
                onFilter = viewModel::filterNext
            )
            if (nextState.openDialogSearchByPhone.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = contentColorFor(MaterialTheme.colors.background)
                                .copy(alpha = 0.6f)
                        ).padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                nextState.openDialogSearchByPhone.value = false
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    SearchByPhoneDialog(nextState.phoneToCheck, nextState.openDialogSearchByPhone, nextState.editPhoneToCheck,
                        nextState.phoneCode, nextState.phoneLength, viewModel::checkTutorByPhone)
                }
            }
        }
        MessageErrorRole(nextState.roleError.value, nextState::showRoleError, viewModel::logout, onLogout)

    }

}

