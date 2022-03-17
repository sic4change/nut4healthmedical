package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child

@ExperimentalCoilApi
@Composable
fun  ChildListItem(
    item: Child,
    onItemMore : (Child) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onItemMore(item) }) {
                    Icon(
                        tint = colorResource(R.color.colorPrimary),
                        imageVector = Icons.Default.ChildCare,
                        contentDescription = null
                    )
                }

                Text(
                    color = colorResource(R.color.colorPrimary),
                    text = "${item.name} ${item.surnames}"  ,
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