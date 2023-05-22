package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.commons.MessageErrorRole
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create.TutorCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create.TutorItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.MessageDeleteTutor
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit.TutorEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit.TutorItemEditScreen
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalPermissionsApi::class
)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorsScreen(viewModel: MainViewModel = viewModel(),
                 onClick: (Tutor) -> Unit,
                 onCreateTutorClick: (String) -> Unit,
                 onLogout: () -> Unit) {
    val mainState = rememberMainState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            mainState.id.value = viewModelState.user!!.id
            mainState.role.value = viewModelState.user!!.role
            mainState.email.value = viewModelState.user!!.email
            mainState.username.value = viewModelState.user!!.username
            viewModel.getPoint(viewModelState.user!!.point)
        }
    }

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            if (viewModelState.tutorChecked == "found") {
                onClick(viewModelState.tutor!!)
                viewModel.resetTutor()
                mainState.phoneToCheck.value = ""
                mainState.editPhoneToCheck.value = ""
            }
            if (!viewModelState.tutor!!.active) {
                onCreateTutorClick(mainState.phoneCode.value + mainState.phoneToCheck.value)
                viewModel.resetTutor()
                mainState.phoneToCheck.value = ""
                mainState.editPhoneToCheck.value = ""
            }
        }
    }

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            mainState.pointId.value = viewModelState.point!!.id
            mainState.point.value = viewModelState.point!!.fullName
            mainState.phoneCode.value = viewModelState.point!!.phoneCode
            mainState.phoneLength.value = viewModelState.point!!.phoneLength
        }
    }


    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                Row {
                    ExtendedFloatingActionButton(
                        onClick = {
                            mainState.openDialogSearchByPhone.value = true
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
            TutorsScreen(onItemClick = onClick)
            if (mainState.openDialogSearchByPhone.value) {
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
                                mainState.openDialogSearchByPhone.value = false
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    SearchByPhoneDialog(mainState.phoneToCheck, mainState.openDialogSearchByPhone, mainState.editPhoneToCheck,
                        mainState.phoneCode, mainState.phoneLength, viewModel::checkTutorByPhone)
                }
            }
        }
        MessageErrorRole(mainState.roleError.value, mainState::showRoleError, viewModel::logout, onLogout)
    }

}




@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun TutorsScreen(onItemClick: (Tutor) -> Unit,
                 viewModel: TutorsViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    TutorItemsListScreen(
        loading = state.loading,
        items = state.tutors,
        onClick = onItemClick,
        onSearch = viewModel::searchTutor
    )
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorDetailScreen(viewModel: TutorDetailViewModel = viewModel(),
                      onEditTutorClick: (Tutor) -> Unit, onChildClick: (Tutor) -> Unit,
                      onDeleteTutorClick: () -> Unit) {
    val tutorDetailState = rememberTutorState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            tutorDetailState.id.value = viewModelState.tutor!!.id
            tutorDetailState.name.value = viewModelState.tutor!!.name
            tutorDetailState.surnames.value = viewModelState.tutor!!.surnames
            tutorDetailState.surnames.value = viewModelState.tutor!!.surnames
            tutorDetailState.address.value = viewModelState.tutor!!.address
            tutorDetailState.phone.value = viewModelState.tutor!!.phone
            tutorDetailState.birthday.value = viewModelState.tutor!!.birthdate
            tutorDetailState.lastDate.value = viewModelState.tutor!!.lastDate
            tutorDetailState.createdDate.value = viewModelState.tutor!!.createDate
            tutorDetailState.sex.value = viewModelState.tutor!!.sex
            tutorDetailState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorDetailState.maleRelation.value = viewModelState.tutor!!.maleRelation
            tutorDetailState.selectedOptionMaleRelations.value = viewModelState.tutor!!.maleRelation
            tutorDetailState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorDetailState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorDetailState.womanStatus.value = viewModelState.tutor!!.womanStatus
            tutorDetailState.selectedOptionWomanStatus.value = viewModelState.tutor!!.womanStatus
            tutorDetailState.weeks.value = viewModelState.tutor!!.weeks
            tutorDetailState.childMinor.value = viewModelState.tutor!!.childMinor
            tutorDetailState.selectedOptionChildMinor.value = viewModelState.tutor!!.childMinor
            tutorDetailState.armCircunference.value = viewModelState.tutor!!.armCircunference
            tutorDetailState.babyAge.value = viewModelState.tutor!!.babyAge
            tutorDetailState.status.value = viewModelState.tutor!!.status
            tutorDetailState.observations.value = viewModelState.tutor!!.observations
        }
    }

    TutorItemDetailScreen(
        loading = viewModelState.loading,
        tutorItem = viewModelState.tutor,
        tutorState = tutorDetailState,
        onEditClick = onEditTutorClick,
        onClickChild = onChildClick,
        onDeleteClick = onDeleteTutorClick
    )
    MessageDeleteTutor(tutorDetailState.deleteTutor.value, tutorDetailState::showDeleteQuestion,
        tutorDetailState.id.value, viewModel::deleteTutor, onDeleteTutorClick)
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorCreateScreen(viewModel: TutorCreateViewModel = viewModel(), onCreateTutor: (String) -> Unit) {
    val tutorCreateState = rememberTutorState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            tutorCreateState.id.value = viewModelState.tutor!!.id
            tutorCreateState.name.value = viewModelState.tutor!!.name
            tutorCreateState.surnames.value = viewModelState.tutor!!.surnames
            tutorCreateState.surnames.value = viewModelState.tutor!!.surnames
            tutorCreateState.address.value = viewModelState.tutor!!.address
            tutorCreateState.phone.value = viewModelState.tutor!!.phone
            tutorCreateState.birthday.value = viewModelState.tutor!!.birthdate
            tutorCreateState.lastDate.value = viewModelState.tutor!!.lastDate
            tutorCreateState.createdDate.value = viewModelState.tutor!!.createDate
            tutorCreateState.sex.value = viewModelState.tutor!!.sex
            tutorCreateState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorCreateState.maleRelation.value = viewModelState.tutor!!.maleRelation
            tutorCreateState.selectedOptionMaleRelations.value = viewModelState.tutor!!.maleRelation
            tutorCreateState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorCreateState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorCreateState.womanStatus.value = viewModelState.tutor!!.womanStatus
            tutorCreateState.selectedOptionWomanStatus.value = viewModelState.tutor!!.womanStatus
            tutorCreateState.weeks.value = viewModelState.tutor!!.weeks
            tutorCreateState.childMinor.value = viewModelState.tutor!!.childMinor
            tutorCreateState.selectedOptionChildMinor.value = viewModelState.tutor!!.childMinor
            tutorCreateState.armCircunference.value = viewModelState.tutor!!.armCircunference
            tutorCreateState.babyAge.value = viewModelState.tutor!!.babyAge
            tutorCreateState.status.value = viewModelState.tutor!!.status
            tutorCreateState.observations.value = viewModelState.tutor!!.observations
        } else {
            tutorCreateState.phone.value = viewModelState.phone
        }
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created) {
            onCreateTutor(viewModelState.id!!)
        }
    }

    TutorItemCreateScreen(
        loading = viewModelState.loading,
        tutorState = tutorCreateState,
        onCreateTutor = viewModel::createTutor
    )
}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorEditScreen(viewModel: TutorEditViewModel = viewModel(), onEditTutor: (String) -> Unit) {
    val tutorEditState = rememberTutorState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            tutorEditState.id.value = viewModelState.tutor!!.id
            tutorEditState.name.value = viewModelState.tutor!!.name
            tutorEditState.surnames.value = viewModelState.tutor!!.surnames
            tutorEditState.surnames.value = viewModelState.tutor!!.surnames
            tutorEditState.address.value = viewModelState.tutor!!.address
            tutorEditState.phone.value = viewModelState.tutor!!.phone
            tutorEditState.birthday.value = viewModelState.tutor!!.birthdate
            tutorEditState.lastDate.value = viewModelState.tutor!!.lastDate
            tutorEditState.createdDate.value = viewModelState.tutor!!.createDate
            tutorEditState.sex.value = viewModelState.tutor!!.sex
            tutorEditState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorEditState.maleRelation.value = viewModelState.tutor!!.maleRelation
            tutorEditState.selectedOptionMaleRelations.value = viewModelState.tutor!!.maleRelation
            tutorEditState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorEditState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorEditState.womanStatus.value = viewModelState.tutor!!.womanStatus
            tutorEditState.selectedOptionWomanStatus.value = viewModelState.tutor!!.womanStatus
            tutorEditState.weeks.value = viewModelState.tutor!!.weeks
            tutorEditState.childMinor.value = viewModelState.tutor!!.childMinor
            tutorEditState.selectedOptionChildMinor.value = viewModelState.tutor!!.childMinor
            tutorEditState.armCircunference.value = viewModelState.tutor!!.armCircunference
            tutorEditState.babyAge.value = viewModelState.tutor!!.babyAge
            tutorEditState.status.value = viewModelState.tutor!!.status
            tutorEditState.observations.value = viewModelState.tutor!!.observations
        }
    }

    LaunchedEffect(viewModelState.editTutor) {
        if (viewModelState.editTutor) {
            onEditTutor(tutorEditState.id.value)
        }
    }


    TutorItemEditScreen(
        loading = viewModelState.loading,
        tutorState = tutorEditState,
        onEditTutor = viewModel::editTutor
    )
}


