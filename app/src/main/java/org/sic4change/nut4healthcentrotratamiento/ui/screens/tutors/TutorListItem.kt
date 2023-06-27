package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.commons.circleLayout

@ExperimentalCoilApi
@Composable
fun  TutorListItem(
    item: Tutor,
    modifier: Modifier = Modifier,
    onClickDetail: (Tutor) -> Unit,
    onClickEdit: (Tutor) -> Unit,
    onClickDelete: (Tutor) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(onClick = { onClickDetail(item); showMenu = false }) {
                        Text(stringResource(R.string.go_to_detail), color = colorResource(R.color.colorPrimary))
                    }
                    DropdownMenuItem(onClick = { onClickEdit(item); showMenu = false }) {
                        Text(stringResource(R.string.editar_tutor), color = colorResource(R.color.colorPrimary))
                    }
                    DropdownMenuItem(onClick = { onClickDelete(item); showMenu = false }) {
                        Text(stringResource(R.string.remove_tutor), color = colorResource(R.color.error))
                    }
                }
            }
        }
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${item.name.slice(0..0)} ${item.surnames.slice(0..0)}",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .background(colorResource(R.color.colorAccent), shape = CircleShape)
                        .circleLayout()
                        .padding(8.dp, 8.dp)
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
                IconButton(onClick = {
                    showMenu = !showMenu
                } ) {
                    Icon(
                        tint = colorResource(R.color.colorPrimary),
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_actions)
                    )
                }
            }
        }

    }

}