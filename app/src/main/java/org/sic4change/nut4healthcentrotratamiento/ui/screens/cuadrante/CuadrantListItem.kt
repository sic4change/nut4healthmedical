package org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.ui.commons.circleLayout
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@Composable
fun  CuadrantListItem(
    item: Cuadrant?,
    onCreateVisitClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (item != null) {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Card {
                Column(
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${item.tutorName.slice(0..0)} ${item.tutorSurname.slice(0..0)}",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .background(colorResource(R.color.colorAccent), shape = CircleShape)
                                .circleLayout()
                                .padding(8.dp, 8.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            color = colorResource(R.color.colorPrimary),
                            text = "${item.tutorName} ${item.tutorSurname}",
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            modifier = Modifier
                                .padding(0.dp, 0.dp)
                                .weight(1f)
                        )
                        IconButton(onClick = {onCreateVisitClick(item.visitsCuadrant[0].caseId) }) {
                            Icon(
                                tint = colorResource(R.color.colorPrimary),
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = stringResource(R.string.more_actions)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Image(
                            painter = painterResource(id = R.mipmap.ic_child),
                            modifier = Modifier.height(40.dp),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            color = colorResource(R.color.colorPrimary),
                            text = "${item.childName}",
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            modifier = Modifier
                                .padding(0.dp, 0.dp)
                                .weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp))

                    Column(verticalArrangement = Arrangement.spacedBy((-20).dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = colorResource(R.color.colorPrimary),
                                text = stringResource(R.string.visits),
                                style = MaterialTheme.typography.body2,
                                maxLines = 2,
                                modifier = Modifier
                                    .padding(8.dp, 8.dp)
                            )
                            item.visitsCuadrant.forEach {
                                var color = colorResource(R.color.colorPrimary)
                                if (formatStatus(it.status) == stringResource(R.string.normopeso)) {
                                    color = colorResource(R.color.colorPrimary)
                                } else if (formatStatus(it.status) == stringResource(R.string.objetive_weight)) {
                                    color = colorResource(R.color.colorPrimary)
                                } else if (formatStatus(it.status) == stringResource(R.string.aguda_moderada)) {
                                    color = colorResource(R.color.orange)
                                } else {
                                    color = colorResource(R.color.error)
                                }
                                Text(
                                    text = " ",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier
                                        .background(color, shape = CircleShape)
                                        .circleLayout()
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                        if (item.visitsCuadrant.isNotEmpty()) {
                            val visit = item.visitsCuadrant[0]
                            var color = colorResource(R.color.colorPrimary)
                            if (formatStatus(visit.status) == stringResource(R.string.normopeso)) {
                                color = colorResource(R.color.colorPrimary)
                            } else if (formatStatus(visit.status) == stringResource(R.string.objetive_weight)) {
                                color = colorResource(R.color.colorPrimary)
                            } else if (formatStatus(visit.status) == stringResource(R.string.aguda_moderada)) {
                                color = colorResource(R.color.orange)
                            } else {
                                color = colorResource(R.color.error)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(enabled = false, onClick = {}) {
                                    Icon(
                                        tint = color,
                                        imageVector = Icons.Default.CalendarToday,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    color = color,
                                    text = stringResource(R.string.last_visits),
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier.padding(8.dp, 0.dp)
                                )
                                Text(
                                    color = color,
                                    text = "${SimpleDateFormat("dd/MM/yyyy").format(visit.createdate)}",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier.padding(8.dp, 0.dp)
                                )
                            }
                        }
                        if (!item.visitsCuadrant.isNullOrEmpty()) {
                            val days = if (item.pointType == "CRENAS") 7 else 14
                            val nextVisit = item.visitsCuadrant[0].createdate.time + (days * 24 * 60 * 60 * 1000)
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(enabled = false, onClick = {}) {
                                    Icon(
                                        tint = colorResource(R.color.colorPrimary),
                                        imageVector = Icons.Default.EditCalendar,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    color = colorResource(R.color.colorPrimary),
                                    text = "${stringResource(R.string.next_visit)}:",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier.padding(8 .dp, 0.dp)
                                )
                                Text(
                                    color = colorResource(R.color.colorPrimary),
                                    text = "${SimpleDateFormat("dd/MM/yyyy").format(nextVisit)}",
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 2,
                                    modifier = Modifier.padding(8.dp, 0.dp)
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