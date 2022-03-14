package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildItemDetailScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun ChildsScreen(viewModel: ChildsViewModel = viewModel(), onClick: (Child) -> Unit,
                 onCreateChildClick: (String) -> Unit) {
    val childsState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.childs) {
        if (viewModelState.childs != null) {
            childsState.childsSize.value = viewModelState.childs!!.size
        }
    }


    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onCreateChildClick(viewModelState.tutorId)
                    },
                    backgroundColor = colorResource(R.color.colorPrimary),
                    content = {
                        Icon(Icons.Filled.Add,"", tint = Color.White)
                    }
                )
            },
        ) {
            ChildItemsListScreen(
                loading = viewModelState.loading,
                items = viewModelState.childs,
                onClick = onClick
            )
        }

    }

}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildDetailScreen(viewModel: ChildDetailViewModel = viewModel(),
                      onEditChildClick: (Child) -> Unit, onCasesClick: (Child) -> Unit,
                      onDeleteChildClick: () -> Unit) {
    val childDetailState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            childDetailState.id.value = viewModelState.child!!.id
            childDetailState.name.value = viewModelState.child!!.name
            childDetailState.surnames.value = viewModelState.child!!.surnames
            childDetailState.surnames.value = viewModelState.child!!.surnames
            childDetailState.birthday.value = viewModelState.child!!.birthdate
            childDetailState.lastDate.value = viewModelState.child!!.lastDate
            childDetailState.createdDate.value = viewModelState.child!!.createDate
            childDetailState.sex.value = viewModelState.child!!.sex
            childDetailState.etnician.value = viewModelState.child!!.ethnicity
            childDetailState.observations.value = viewModelState.child!!.observations
            childDetailState.selectedOptionSex.value = viewModelState.child!!.sex
            childDetailState.selectedOptionEtnician.value = viewModelState.child!!.ethnicity
        }
    }

    ChildItemDetailScreen(
        loading = viewModelState.loading,
        childItem = viewModelState.child,
        childState = childDetailState,
        onEditClick = onEditChildClick,
        onCasesClick = onCasesClick,
        onDeleteClick = onDeleteChildClick
    )
    /*MessageDeleteTutor(tutorDetailState.deleteTutor.value, tutorDetailState::showDeleteQuestion,
        tutorDetailState.id.value, viewModel::deleteTutor, onDeleteTutorClick)*/
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
            childCreateState.lastDate.value = viewModelState.child!!.lastDate
            childCreateState.createdDate.value = viewModelState.child!!.createDate
            childCreateState.sex.value = viewModelState.child!!.sex
            childCreateState.etnician.value = viewModelState.child!!.ethnicity
            childCreateState.observations.value = viewModelState.child!!.observations
            childCreateState.selectedOptionSex.value = viewModelState.child!!.sex
            childCreateState.selectedOptionEtnician.value = viewModelState.child!!.ethnicity
        }
    }

    //El problema es que siempre entra Aqui
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

