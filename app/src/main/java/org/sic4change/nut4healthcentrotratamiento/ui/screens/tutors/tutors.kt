package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors


import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create.TutorCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create.TutorItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.MessageDeleteTutor
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit.TutorEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit.TutorItemEditScreen

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorsScreen(viewModel: MainViewModel = viewModel(), onClick: (Tutor) -> Unit,
    onCreateTutorClick: (String) -> Unit) {
    val mainState = rememberMainState()
    val viewModelState by viewModel.state.collectAsState()
    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            mainState.id.value = viewModelState.user!!.id
            mainState.role.value = viewModelState.user!!.role
            mainState.email.value = viewModelState.user!!.email
            mainState.username.value = viewModelState.user!!.username
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
                onCreateTutorClick(mainState.phoneToCheck.value)
                viewModel.resetTutor()
                mainState.phoneToCheck.value = ""
                mainState.editPhoneToCheck.value = ""
            }
        }
    }

    BackHandler {
        activity?.finish()
    }

    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                Row {
                    FloatingActionButton(
                        onClick = {
                            mainState.openDialogSearchByNameAndSurname.value = true
                        },
                        backgroundColor = colorResource(R.color.colorPrimary),
                        content = {
                            Icon(Icons.Filled.Search,"", tint = Color.White)
                        }
                    )
                    Box(Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = {
                            mainState.openDialogSearchByPhone.value = true
                        },
                        backgroundColor = colorResource(R.color.colorPrimary),
                        content = {
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
                        viewModel::checkTutorByPhone)
                }
            }
            if (mainState.openDialogSearchByNameAndSurname.value) {
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
                                mainState.openDialogSearchByNameAndSurname.value = false
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    SearchByPhoneDialog(mainState.phoneToCheck, mainState.openDialogSearchByPhone, mainState.editPhoneToCheck,
                        viewModel::checkTutorByPhone)
                }
            }
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun TutorsScreen(onItemClick: (Tutor) -> Unit, viewModel: TutorsViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()


    TutorItemsListScreen(
        loading = state.loading,
        items = state.tutors,
        onClick = onItemClick
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
            tutorDetailState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorDetailState.pregnant.value = viewModelState.tutor!!.pregnant
            tutorDetailState.weeks.value = viewModelState.tutor!!.weeks
            tutorDetailState.observations.value = viewModelState.tutor!!.observations
            tutorDetailState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorDetailState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorDetailState.selectedOptionPregnant.value = viewModelState.tutor!!.pregnant
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
fun TutorCreateScreen(viewModel: TutorCreateViewModel = viewModel(), onCreateTutor: () -> Unit) {
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
            tutorCreateState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorCreateState.pregnant.value = viewModelState.tutor!!.pregnant
            tutorCreateState.weeks.value = viewModelState.tutor!!.weeks
            tutorCreateState.observations.value = viewModelState.tutor!!.observations
            tutorCreateState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorCreateState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorCreateState.selectedOptionPregnant.value = viewModelState.tutor!!.pregnant
        } else {
            tutorCreateState.phone.value = viewModelState.phone
        }
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created) {
            onCreateTutor()
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
fun TutorEditScreen(viewModel: TutorEditViewModel = viewModel(), onEditTutor: () -> Unit) {
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
            tutorEditState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorEditState.pregnant.value = viewModelState.tutor!!.pregnant
            tutorEditState.weeks.value = viewModelState.tutor!!.weeks
            tutorEditState.observations.value = viewModelState.tutor!!.observations
            tutorEditState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorEditState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorEditState.selectedOptionPregnant.value = viewModelState.tutor!!.pregnant
        }
    }

    LaunchedEffect(viewModelState.editTutor) {
        if (viewModelState.editTutor) {
            onEditTutor()
        }
    }


    TutorItemEditScreen(
        loading = viewModelState.loading,
        tutorState = tutorEditState,
        onEditTutor = viewModel::editTutor
    )
}


