package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sic4change.nut4healthcentrotratamiento.MainActivity
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.SearchByPhoneDialog

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun NextScreen(
    onItemClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    onClick: (Tutor) -> Unit,
    onCreateTutorClick: (String) -> Unit,
    viewModel: NextsViewModel= viewModel()) {
    val nextState = rememberNextState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            nextState.id.value = viewModelState.user!!.id
            viewModel.getPoint(viewModelState.user!!.point)
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
            }
            if (!viewModelState.tutor!!.active) {
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
                        )
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

    }

}

