package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@Composable
fun  VisitListItem(
    item: Visit,
    onItemMore : (Visit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                    IconButton(onClick = {}) {
                        Icon(
                            tint = colorResource(R.color.colorPrimary),
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = null
                        )
                    }
                    Text(
                        color = colorResource(R.color.colorPrimary),
                        text = "${item.status}".toString().capitalize()  ,
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(8.dp, 16.dp)
                            .weight(1f)
                    )
                    Text(
                        color = colorResource(R.color.colorPrimary),
                        text = "${SimpleDateFormat("dd/MM/yyyy").format(item.createdate)}",
                        style = MaterialTheme.typography.h6,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(8.dp, 16.dp)
                            .weight(1f)
                    )
                    IconButton(onClick = { onItemMore(item) }) {
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