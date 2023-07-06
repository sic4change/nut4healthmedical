package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.MessageDeleteCase
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.MessageDeleteCaseFromChild
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.MessageDeleteChild
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit.ChildEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit.ChildItemEditScreen


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildDetailScreen(viewModel: ChildDetailViewModel = viewModel(),
                      onEditChildClick: (Child) -> Unit,
                      onCaseCreated: (Case) -> Unit,
                      onItemClick: (Case) -> Unit,
                      onDeleteChildClick: (String) -> Unit,
                      onClickDetail: (Case) -> Unit,
                      onClickEdit: (Case) -> Unit,
                      onClickDelete: (Case) -> Unit,
                      onDeleteCase: (String) -> Unit,) {

    val childDetailState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            childDetailState.id.value = viewModelState.child!!.id
            childDetailState.tutorId.value = viewModelState.child!!.tutorId
            childDetailState.name.value = viewModelState.child!!.name
            childDetailState.surnames.value = viewModelState.child!!.surnames
            childDetailState.surnames.value = viewModelState.child!!.surnames
            childDetailState.birthday.value = viewModelState.child!!.birthdate
            childDetailState.brothers.value = viewModelState.child!!.brothers
            childDetailState.lastDate.value = viewModelState.child!!.lastDate
            childDetailState.createdDate.value = viewModelState.child!!.createDate
            childDetailState.sex.value = viewModelState.child!!.sex
            childDetailState.etnician.value = viewModelState.child!!.ethnicity
            childDetailState.observations.value = viewModelState.child!!.observations
            childDetailState.selectedOptionBrothers.value = viewModelState.child!!.brothers
            childDetailState.selectedOptionSex.value = viewModelState.child!!.sex
            childDetailState.selectedOptionEtnician.value = viewModelState.child!!.ethnicity
        }
    }

    LaunchedEffect(viewModelState.newCaseCreated) {
        if (viewModelState.newCaseCreated) {
            onCaseCreated(viewModelState.newCase!!)
        }
    }

    ChildItemDetailScreen(
        loading = viewModelState.loading,
        childItem = viewModelState.child,
        cases = viewModelState.cases,
        childState = childDetailState,
        onEditClick = onEditChildClick,
        onCreateCaseClick = viewModel::createCase,
        onItemClick = onItemClick,
        onClickDetail = onClickDetail,
        onClickEdit = onClickEdit,
        onClickDelete = {childDetailState.showDeleteCaseQuestion(it.id)},
        onDeleteCase = onDeleteCase,
    )

    MessageDeleteChild(childDetailState.deleteChild.value, childDetailState::showDeleteQuestion,
        childDetailState.id.value, childDetailState.tutorId.value, viewModel::deleteChild, onDeleteChildClick)

    MessageDeleteCaseFromChild(childDetailState.deleteCase.value, childDetailState::showDeleteCaseQuestion,
        childDetailState.caseId.value, viewModel::deleteCase, childDetailState.id.value, onDeleteCase)

}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildCreateScreen(viewModel: ChildCreateViewModel = viewModel(), onCreateChild: (String) -> Unit) {
    val childCreateState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            childCreateState.id.value = viewModelState.child!!.id
            childCreateState.tutorId.value = viewModelState.child!!.tutorId
            childCreateState.name.value = viewModelState.child!!.name
            childCreateState.surnames.value = viewModelState.child!!.surnames
            childCreateState.birthday.value = viewModelState.child!!.birthdate
            childCreateState.brothers.value = viewModelState.child!!.brothers
            childCreateState.lastDate.value = viewModelState.child!!.lastDate
            childCreateState.createdDate.value = viewModelState.child!!.createDate
            childCreateState.sex.value = viewModelState.child!!.sex
            childCreateState.etnician.value = viewModelState.child!!.ethnicity
            childCreateState.observations.value = viewModelState.child!!.observations
            childCreateState.selectedOptionBrothers.value = viewModelState.child!!.brothers
            childCreateState.selectedOptionSex.value = viewModelState.child!!.sex
            childCreateState.selectedOptionEtnician.value = viewModelState.child!!.ethnicity
        }
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created) {
            onCreateChild(childCreateState.tutorId.value)
        }
    }

    ChildItemCreateScreen(
        loading = viewModelState.loading,
        childState = childCreateState,
        onCreateChild = viewModel::createChild
    )
}



@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildEditScreen(viewModel: ChildEditViewModel = viewModel(), onEditChild: (String) -> Unit) {
    val childEditState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            childEditState.id.value = viewModelState.child!!.id
            childEditState.tutorId.value = viewModelState.child!!.tutorId
            childEditState.name.value = viewModelState.child!!.name
            childEditState.surnames.value = viewModelState.child!!.surnames
            childEditState.birthday.value = viewModelState.child!!.birthdate
            childEditState.brothers.value = viewModelState.child!!.brothers
            childEditState.selectedOptionBrothers.value = viewModelState.child!!.brothers
            childEditState.lastDate.value = viewModelState.child!!.lastDate
            childEditState.createdDate.value = viewModelState.child!!.createDate
            childEditState.sex.value = viewModelState.child!!.sex
            childEditState.etnician.value = viewModelState.child!!.ethnicity
            childEditState.observations.value = viewModelState.child!!.observations
            childEditState.selectedOptionBrothers.value = viewModelState.child!!.brothers
            childEditState.selectedOptionSex.value = viewModelState.child!!.sex
            childEditState.selectedOptionEtnician.value = viewModelState.child!!.ethnicity
        }
    }

    LaunchedEffect(viewModelState.editChild) {
        if (viewModelState.editChild) {
            onEditChild(childEditState.id.value)
        }
    }

    ChildItemEditScreen(
        loading = viewModelState.loading,
        childState = childEditState,
        onEditChild = viewModel::editChild
    )
}
