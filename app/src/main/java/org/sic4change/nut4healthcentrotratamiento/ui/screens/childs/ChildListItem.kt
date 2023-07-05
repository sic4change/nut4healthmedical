package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor

@ExperimentalCoilApi
@Composable
fun  ChildListItem(
    item: Child,
    modifier: Modifier = Modifier,
    onClickDetail: (Child) -> Unit,
    onClickEdit: (Child) -> Unit,
    onClickDelete: (Child) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.mipmap.ic_child),
                    contentDescription = null
                )
                Text(
                    color = colorResource(R.color.colorPrimary),
                    text = "${item.name} ${item.surnames}"  ,
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
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(onClick = { onClickDetail(item); showMenu = false }) {
                    Text(stringResource(R.string.go_to_detail), color = colorResource(R.color.colorPrimary))
                }
                DropdownMenuItem(onClick = { onClickEdit(item); showMenu = false }) {
                    Text(stringResource(R.string.edit_child), color = colorResource(R.color.colorPrimary))
                }
                DropdownMenuItem(onClick = { onClickDelete(item); showMenu = false }) {
                    Text(stringResource(R.string.remove_child), color = colorResource(R.color.error))
                }
            }
        }

    }
}