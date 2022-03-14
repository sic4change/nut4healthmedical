package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.AppBarIcon
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState

@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScaffold(
    tutorState: TutorState,
    tutorItem: Tutor,
    onClickEdit: (Tutor) -> Unit,
    onChildClick: (Tutor) -> Unit,
    onClickDelete: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,

    ) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = colorResource(R.color.colorAccent),
                onClick = { onChildClick(tutorItem) },
                shape = MaterialTheme.shapes.small
            ) {
                Icon(imageVector = Icons.Default.ChildCare, contentDescription = null, tint = colorResource(R.color.white))

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                backgroundColor = colorResource(R.color.colorPrimary),
                cutoutShape = MaterialTheme.shapes.small
            ) {
                AppBarIcon(imageVector = Icons.Default.Edit, onClick = {
                    onClickEdit(tutorItem)
                })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Delete, onClick = {
                    tutorState.showDeleteQuestion()
                    //onClickDelete(tutorItem)
                })
            }
        },
        content = content
    )
}
