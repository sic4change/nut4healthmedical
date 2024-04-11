package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.commons.circleLayout

@ExperimentalCoilApi
@Composable
fun  ReferenceTransferenceListItem(
    child: Child?,
    tutor: Tutor?,
    derivation: Derivation,
    modifier: Modifier = Modifier,
    onClickDetail: (Derivation) -> Unit,
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
                    DropdownMenuItem(onClick = { onClickDetail(derivation); showMenu = false }) {
                        Text(stringResource(R.string.go_to_detail), color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                if (child != null) {
                    Text(
                        text = "${child.name.slice(0..0)} ${child.surnames.slice(0..0)}",
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .background(colorResource(R.color.colorAccent), shape = CircleShape)
                            .circleLayout()
                            .padding(8.dp, 8.dp)
                    )
                } else {
                    if (tutor != null) {
                        Text(
                            text = "${tutor!!.name.slice(0..0)} ${tutor.surnames.slice(0..0)}",
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier
                                .background(colorResource(R.color.colorAccent), shape = CircleShape)
                                .circleLayout()
                                .padding(8.dp, 8.dp)
                        )
                    }

                }

                Column(modifier = Modifier
                        .padding(8.dp, 16.dp)
                        .weight(1f)
                )
                {
                    if (child != null) {
                        Text(
                            color = colorResource(R.color.colorPrimary),
                            text = "${child.name} ${child.surnames}"  ,
                            style = MaterialTheme.typography.h5,
                            maxLines = 2,
                        )
                    } else {
                        if (tutor != null) {
                            Text(
                                color = colorResource(R.color.colorPrimary),
                                text = "${tutor!!.name} ${tutor.surnames}"  ,
                                style = MaterialTheme.typography.h5,
                                maxLines = 2,
                            )
                        }

                    }
                    Text(
                        color = colorResource(R.color.error),
                        text = "${derivation.code}"  ,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                    )
                }

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