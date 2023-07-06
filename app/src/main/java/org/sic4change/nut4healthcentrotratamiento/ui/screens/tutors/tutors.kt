package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors


import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi

import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.commons.MessageErrorRole
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create.TutorCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create.TutorItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.MessageDeleteChild
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.MessageDeleteTutor
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.fefa.FEFAItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.fefa.FEFAViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit.TutorEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit.TutorItemEditScreen

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorsScreen(viewModel: MainViewModel = viewModel(),
                 onClick: (Tutor) -> Unit,
                 onCreateTutorClick: (String) -> Unit,
                 onClickDetail: (Tutor) -> Unit,
                 onClickEdit: (Tutor) -> Unit,
                 onClickDelete: (Tutor) -> Unit,
                 onDeleteTutor: () -> Unit,
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
            TutorsScreen(
                onItemClick = onClick,
                onClickDetail = onClickDetail,
                onClickEdit = onClickEdit,
                onClickDelete = onClickDelete,
                onDeleteTutor = onDeleteTutor)
            if (mainState.openDialogSearchByPhone.value) {
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
fun TutorsScreen(
    onItemClick: (Tutor) -> Unit,
    onClickDetail: (Tutor) -> Unit,
    onClickEdit: (Tutor) -> Unit,
    onClickDelete: (Tutor) -> Unit,
    onDeleteTutor: () -> Unit,
    viewModel: TutorsViewModel = viewModel()) {

    val tutorsState = rememberTutorState()
    val state by viewModel.state.collectAsState()

    TutorItemsListScreen(
        loading = state.loading,
        items = state.tutors,
        onClick = onItemClick,
        onClickDetail = onClickDetail,
        onClickEdit = onClickEdit,
        onClickDelete = {tutorsState.showDeleteQuestion(it.id)},
        onDeleteTutor = onDeleteTutor,
        onSearch = viewModel::searchTutor
    )

    MessageDeleteTutor(tutorsState.deleteTutor.value, tutorsState::showDeleteQuestion,
        tutorsState.id.value, viewModel::deleteTutor, onDeleteTutor)
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun FEFADetailScreen(
    viewModel: FEFAViewModel = viewModel(),
    onEditTutorClick: (Tutor) -> Unit,
    onDeleteTutorClick: () -> Unit,
    onCaseCreated: (Case) -> Unit,
    onTutorDeleted: () -> Unit,
) {

    val fefaDetailState = rememberTutorState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.tutor) {
        if (viewModelState.tutor != null) {
            fefaDetailState.id.value = viewModelState.tutor!!.id
            fefaDetailState.name.value = viewModelState.tutor!!.name
            fefaDetailState.surnames.value = viewModelState.tutor!!.surnames
            fefaDetailState.surnames.value = viewModelState.tutor!!.surnames
            fefaDetailState.address.value = viewModelState.tutor!!.address
            fefaDetailState.phone.value = viewModelState.tutor!!.phone
            fefaDetailState.birthday.value = viewModelState.tutor!!.birthdate
            fefaDetailState.lastDate.value = viewModelState.tutor!!.lastDate
            fefaDetailState.createdDate.value = viewModelState.tutor!!.createDate
            fefaDetailState.sex.value = viewModelState.tutor!!.sex
            fefaDetailState.selectedOptionSex.value = viewModelState.tutor!!.sex
            fefaDetailState.maleRelation.value = viewModelState.tutor!!.maleRelation
            fefaDetailState.selectedOptionMaleRelations.value = viewModelState.tutor!!.maleRelation
            fefaDetailState.etnician.value = viewModelState.tutor!!.ethnicity
            fefaDetailState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            fefaDetailState.womanStatus.value = viewModelState.tutor!!.womanStatus
            fefaDetailState.selectedOptionWomanStatus.value = viewModelState.tutor!!.womanStatus
            fefaDetailState.weeks.value = viewModelState.tutor!!.weeks
            fefaDetailState.childMinor.value = viewModelState.tutor!!.childMinor
            fefaDetailState.selectedOptionChildMinor.value = viewModelState.tutor!!.childMinor
            fefaDetailState.babyAge.value = viewModelState.tutor!!.babyAge
            fefaDetailState.observations.value = viewModelState.tutor!!.observations
        }
    }

    LaunchedEffect(viewModelState.newCaseCreated) {
        if (viewModelState.newCaseCreated) {
            onCaseCreated(viewModelState.newCase!!)
        }
    }

    FEFAItemDetailScreen(
        loading = viewModelState.loading,
        tutorItem = viewModelState.tutor,
        cases = viewModelState.cases,
        fefaState = fefaDetailState,
        onClickDelete = {fefaDetailState.showDeleteQuestion()},
        onTutorDeleted = onTutorDeleted,
        onEditClick = onEditTutorClick,
        onCreateCaseClick = viewModel::createCase,
    )

    MessageDeleteTutor(fefaDetailState.deleteTutor.value, fefaDetailState::showDeleteQuestion,
        fefaDetailState.id.value, viewModel::deleteTutor, onTutorDeleted)

}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorDetailScreen(
    viewModel: TutorDetailViewModel = viewModel(),
    onFEFAClick: (Tutor) -> Unit,
    onEditTutorClick: (Tutor) -> Unit,
    onCreateChildClick: (Tutor) -> Unit,
    onItemClick: (Child) -> Unit,
    onDeleteTutorClick: () -> Unit,
    onClickDetail: (Child) -> Unit,
    onClickEdit: (Child) -> Unit,
    onClickDelete: (Child) -> Unit,
    onDeleteChild: (String) -> Unit) {

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
            tutorDetailState.babyAge.value = viewModelState.tutor!!.babyAge
            tutorDetailState.observations.value = viewModelState.tutor!!.observations
        }
    }

    TutorItemDetailScreen(
        loading = viewModelState.loading,
        tutorItem = viewModelState.tutor,
        childs = viewModelState.childs,
        onFEFAClick = onFEFAClick,
        tutorState = tutorDetailState,
        onEditClick = onEditTutorClick,
        onCreateChildClick = onCreateChildClick,
        onItemClick = onItemClick,
        onClickDetail = onClickDetail,
        onClickEdit = onClickEdit,
        onClickDelete = {tutorDetailState.showDeleteChildQuestion(it.id)},
        onDeleteChild = onDeleteChild,
    )

    MessageDeleteTutor(tutorDetailState.deleteTutor.value, tutorDetailState::showDeleteQuestion,
        tutorDetailState.id.value, viewModel::deleteTutor, onDeleteTutorClick)

    MessageDeleteChild(tutorDetailState.deleteChild.value, tutorDetailState::showDeleteChildQuestion,
        tutorDetailState.childId.value, viewModel::deleteChild, tutorDetailState.id.value, onDeleteChild)
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
            tutorCreateState.babyAge.value = viewModelState.tutor!!.babyAge
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
            tutorEditState.babyAge.value = viewModelState.tutor!!.babyAge
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


