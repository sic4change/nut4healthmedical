package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@Composable
fun  VisitListItem(
    item: Visit,
    onItemMore : (Visit) -> Unit,
    modifier: Modifier = Modifier
) {
    var colorBackground : Color = Color.White
    if (formatStatus(item.status)  == stringResource(R.string.normopeso)) {
        colorBackground = colorResource(R.color.colorAccent)
    } else if (formatStatus(item.status)  == stringResource(R.string.objetive_weight)) {
        colorBackground = colorResource(R.color.colorPrimary)
    } else if (formatStatus(item.status)  == stringResource(R.string.aguda_moderada)) {
        colorBackground = colorResource(R.color.orange)
    } else {
        colorBackground = colorResource(R.color.error)
    }
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card(
            backgroundColor = colorBackground,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = colorResource(R.color.white),
                    text = "${formatStatus(item.status) }".toString().capitalize()  ,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(16.dp, 16.dp)
                        .weight(2f)
                )
                Text(
                    color = colorResource(R.color.white),
                    text = "${SimpleDateFormat("dd/MM/yyyy").format(item.createdate)}",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold),
                    maxLines = 2,
                    modifier = Modifier
                        .padding(16.dp, 16.dp)
                        .weight(1f)
                )
                /*IconButton(onClick = { onItemMore(item) }) {
                    Icon(
                        tint = colorResource(R.color.white),
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_actions)
                    )
                }*/

            }
        }
    }
}