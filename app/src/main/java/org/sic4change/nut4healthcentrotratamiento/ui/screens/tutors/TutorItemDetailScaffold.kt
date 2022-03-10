package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors


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

@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScaffold(
    tutorItem: Tutor,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = colorResource(R.color.colorAccent),
                onClick = {  },
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

                })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Delete, onClick = { })
            }
        },
        content = content
    )
}
