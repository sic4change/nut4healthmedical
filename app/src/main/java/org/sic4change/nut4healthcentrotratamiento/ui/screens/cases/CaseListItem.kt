package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child

@ExperimentalCoilApi
@Composable
fun  CaseListItem(
    item: Case,
    modifier: Modifier = Modifier,
    onClickDetail: (Case) -> Unit,
    onClickEdit: (Case) -> Unit,
    onClickDelete: (Case) -> Unit,
    onClickTransfered: (Case) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.status == stringResource(R.string.open)) {
                    IconButton(onClick = {}) {
                        Icon(
                            tint = colorResource(R.color.colorPrimary),
                            painter = painterResource(id = R.drawable.ic_open_case),
                            contentDescription = null
                        )
                    }
                    Text(
                        color = colorResource(R.color.colorPrimary),
                        text = "${item.name}" ,
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(8.dp, 16.dp)
                            .weight(1f)
                    )
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            tint = colorResource(R.color.colorPrimary),
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_actions)
                        )
                    }
                } else {
                    IconButton(onClick = { }) {
                        Icon(
                            tint = colorResource(R.color.colorPrimary),
                            painter = painterResource(id = R.drawable.ic_closed_case),
                            contentDescription = null
                        )
                    }
                    Text(
                        color = colorResource(R.color.colorPrimary),
                        text = "${item.name}",
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(8.dp, 16.dp)
                            .weight(1f)
                    )
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            tint = colorResource(R.color.colorPrimary),
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_actions)
                        )
                    }
                }
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(onClick = { onClickDetail(item); showMenu = false }) {
                    Text(stringResource(R.string.go_to_detail), color = colorResource(R.color.colorPrimary))
                }
                DropdownMenuItem(onClick = { onClickEdit(item); showMenu = false }) {
                    Text(stringResource(R.string.edit_case), color = colorResource(R.color.colorPrimary))
                }
                if (item.status == stringResource(R.string.open)) {
                    DropdownMenuItem(onClick = { onClickTransfered(item); showMenu = false }) {
                        Text(stringResource(R.string.trasferred_case), color = colorResource(R.color.colorPrimary))
                    }
                }
                DropdownMenuItem(onClick = { onClickDelete(item); showMenu = false }) {
                    Text(stringResource(R.string.remove_case), color = colorResource(R.color.error))
                }
            }
        }

    }
}