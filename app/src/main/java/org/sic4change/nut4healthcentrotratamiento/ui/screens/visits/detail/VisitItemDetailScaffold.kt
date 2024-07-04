package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.AppBarIcon
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState

@ExperimentalMaterialApi
@Composable
fun VisitItemDetailScaffold(
    visitState: VisitState,
    visitItem: Visit,
    onClickEdit: (Visit) -> Unit,
    onClickDelete: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit,

    ) {

    Scaffold(

        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                backgroundColor = colorResource(R.color.colorPrimary),
                cutoutShape = MaterialTheme.shapes.small
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Delete, onClick = {
                    visitState.showDeleteQuestion()
                })
            }
        },
        content = content
    )
}
