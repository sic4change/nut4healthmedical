package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors


import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.*
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState

@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorsScreen(viewModel: MainViewModel = viewModel(), onClick: (Tutor) -> Unit) {
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

    BackHandler {
        activity?.finish()
    }

    NUT4HealthScreen {
        TutorsScreen(onItemClick = onClick)
    }

}


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
fun TutorDetailScreen(viewModel: TutorDetailViewModel = viewModel()) {
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
            tutorDetailState.sex.value = viewModelState.tutor!!.sex
            tutorDetailState.etnician.value = viewModelState.tutor!!.ethnicity
            tutorDetailState.pregnant.value = viewModelState.tutor!!.pregnant
            tutorDetailState.observations.value = viewModelState.tutor!!.observations
            tutorDetailState.selectedOptionSex.value = viewModelState.tutor!!.sex
            tutorDetailState.selectedOptionEtnician.value = viewModelState.tutor!!.ethnicity
            tutorDetailState.selectedOptionPregnant.value = viewModelState.tutor!!.pregnant
        }
    }

    TutorItemDetailScreen(
        loading = viewModelState.loading,
        tutorItem = viewModelState.tutor,
        tutorState = tutorDetailState
    )
}

