package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case

@ExperimentalCoilApi
@Composable
fun  CaseListItem(
    item: Case,
    onItemMore : (Case) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.status == "open") {
                    Text(
                        color = colorResource(R.color.colorAccent),
                        text = "${item.status}".toString().capitalize()  ,
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(8.dp, 16.dp)
                            .weight(1f)
                    )
                    IconButton(onClick = { onItemMore(item) }) {
                        Icon(
                            tint = colorResource(R.color.colorAccent),
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_actions)
                        )
                    }
                } else {
                    Text(
                        color = colorResource(R.color.colorPrimary),
                        text = "${item.status}".toString().capitalize()  ,
                        style = MaterialTheme.typography.h5,
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
}