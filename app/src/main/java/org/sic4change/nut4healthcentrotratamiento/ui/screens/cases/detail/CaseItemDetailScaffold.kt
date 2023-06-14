package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.AppBarIcon
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseState

@ExperimentalMaterialApi
@Composable
fun CaseItemDetailScaffold(
    caseState: CaseState,
    caseItem: Case,
    onClickEdit: (Case) -> Unit,
    content: @Composable (PaddingValues) -> Unit,

    ) {

    Scaffold(
        bottomBar = {
            BottomAppBar(
                backgroundColor = colorResource(R.color.colorPrimary),
                cutoutShape = MaterialTheme.shapes.small
            ) {
                AppBarIcon(imageVector = Icons.Default.Edit, onClick = {
                    onClickEdit(caseItem)
                })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Delete, onClick = {
                    caseState.showDeleteQuestion()
                })
            }
        },
        content = content
    )
}
