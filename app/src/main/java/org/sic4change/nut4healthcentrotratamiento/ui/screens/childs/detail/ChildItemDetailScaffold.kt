package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail


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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState

@ExperimentalMaterialApi
@Composable
fun ChildItemDetailScaffold(
    childState: ChildState,
    childItem: Child,
    onClickEdit: (Child) -> Unit,
    onCasesClick: (Child) -> Unit,
    onClickDelete: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit,

    ) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = colorResource(R.color.colorAccent),
                onClick = { onCasesClick(childItem) },
                shape = MaterialTheme.shapes.small
            ) {
                Icon(imageVector = Icons.Default.Folder, contentDescription = null, tint = colorResource(R.color.white))

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
                    onClickEdit(childItem)
                })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Delete, onClick = {
                    childState.showDeleteQuestion()
                })
            }
        },
        content = content
    )
}
