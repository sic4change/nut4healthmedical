package org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@Composable
fun  CuadrantListItem(
    item: Cuadrant?,
    modifier: Modifier = Modifier
) {
    if (item != null) {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Card {
                Column(
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                tint = colorResource(R.color.colorPrimary),
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        }
                        Text(
                            color = colorResource(R.color.colorPrimary),
                            text = "${item.tutorName}".toString().capitalize()  ,
                            style = MaterialTheme.typography.h5,
                            maxLines = 2,
                            modifier = Modifier
                                .padding(0.dp, 0.dp)
                                .weight(1f)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                tint = colorResource(R.color.colorPrimary),
                                imageVector = Icons.Default.ChildCare,
                                modifier = Modifier.size(24.dp),
                                contentDescription = null
                            )
                        }
                        Text(
                            color = colorResource(R.color.colorPrimary),
                            text = "${item.childName}".toString().capitalize()  ,
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            modifier = Modifier
                                .padding(0.dp, 0.dp)
                                .weight(1f)
                        )
                    }

                    Divider(color = Color.LightGray, thickness = 2.dp, modifier =
                    Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp))

                    if (!item.visitsCuadrant.isNullOrEmpty()) {
                        val nextVisit = item.visitsCuadrant[0].createdate.time + (14 * 24 * 60 * 60 * 1000)
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {}) {
                                Icon(
                                    tint = colorResource(R.color.frutorial_title),
                                    imageVector = Icons.Default.Add,
                                    modifier = Modifier.size(20.dp),
                                    contentDescription = null
                                )
                            }
                            Text(
                                color = colorResource(R.color.frutorial_title),
                                text = stringResource(R.string.next_visit).capitalize(),
                                style = MaterialTheme.typography.body2,
                                maxLines = 2,
                                modifier = Modifier
                                    .padding(8.dp, 16.dp)
                                    .weight(1f)
                            )
                            Text(
                                color = colorResource(R.color.frutorial_title),
                                text = "${SimpleDateFormat("dd/MM/yyyy").format(nextVisit)}",
                                style = MaterialTheme.typography.body2,
                                maxLines = 2,
                                modifier = Modifier
                                    .padding(8.dp, 16.dp)
                                    .weight(1f)
                            )
                        }
                    }

                    item.visitsCuadrant.forEach { visit ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (formatStatus(visit.status)  == stringResource(R.string.normopeso)) {
                                IconButton(onClick = {}) {
                                    Icon(
                                        tint = colorResource(R.color.colorAccent),
                                        imageVector = Icons.Default.EditCalendar,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    color = colorResource(R.color.colorAccent),
                                    text = "${formatStatus(visit.status) }".toString().capitalize()  ,
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                                Text(
                                    color = colorResource(R.color.colorAccent),
                                    text = "${SimpleDateFormat("dd/MM/yyyy").format(visit.createdate)}",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                            } else if  (formatStatus(visit.status)  == stringResource(R.string.objetive_weight)) {
                                IconButton(onClick = {}) {
                                    Icon(
                                        tint = colorResource(R.color.colorPrimary),
                                        imageVector = Icons.Default.EditCalendar,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    color = colorResource(R.color.colorPrimary),
                                    text = "${formatStatus(visit.status) }".toString().capitalize()  ,
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                                Text(
                                    color = colorResource(R.color.colorPrimary),
                                    text = "${SimpleDateFormat("dd/MM/yyyy").format(visit.createdate)}",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                            } else if (formatStatus(visit.status)  == stringResource(R.string.aguda_moderada)) {
                                IconButton(onClick = {}) {
                                    Icon(
                                        tint = colorResource(R.color.orange),
                                        imageVector = Icons.Default.EditCalendar,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    color = colorResource(R.color.orange),
                                    text = "${formatStatus(visit.status) }".toString().capitalize()  ,
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                                Text(
                                    color = colorResource(R.color.orange),
                                    text = "${SimpleDateFormat("dd/MM/yyyy").format(visit.createdate)}",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                            } else {
                                IconButton(onClick = {}) {
                                    Icon(
                                        tint = colorResource(R.color.error),
                                        imageVector = Icons.Default.EditCalendar,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    color = colorResource(R.color.error),
                                    text = "${formatStatus(visit.status) }".toString().capitalize()  ,
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                                Text(
                                    color = colorResource(R.color.error),
                                    text = "${SimpleDateFormat("dd/MM/yyyy").format(visit.createdate)}",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(8.dp, 16.dp)
                                        .weight(1f)
                                )
                            }


                        }
                    }
                }

            }
        }
    } else {
        Spacer(modifier = Modifier.height(0.dp))
    }

}