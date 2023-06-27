package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts

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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@Composable
fun NextListItem(
    item: Cuadrant?,
    modifier: Modifier = Modifier,
    onCreateVisitClick: (String) -> Unit,
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
                        IconButton(onClick = {}) {
                            Icon(
                                tint = colorResource(R.color.colorPrimary),
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        }
                        Text(
                            color = colorResource(R.color.colorPrimary),
                            text = "${item.tutorName}",
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
                        IconButton(onClick = {}) {
                            Icon(
                                tint = colorResource(R.color.colorPrimary),
                                imageVector = Icons.Default.ChildCare,
                                modifier = Modifier.size(24.dp),
                                contentDescription = null
                            )
                        }
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

                    Divider(
                        color = Color.LightGray,
                        thickness = 2.dp,
                        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
                    )

                    if (item.visitsCuadrant != null && item.visitsCuadrant.isNotEmpty()) {
                        val days = if (item.pointType == "CRENAS") 7 else 14
                        val nextVisit = item.visitsCuadrant[0].createdate.time + (days * 24 * 60 * 60 * 1000)
                        val visit = item.visitsCuadrant[0]
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            IconButton(onClick = {
                                onCreateVisitClick(item.visitsCuadrant[0].caseId)
                            }) {
                                Icon(
                                    tint = colorResource(R.color.frutorial_title),
                                    imageVector = Icons.Default.Add,
                                    modifier = Modifier.size(16.dp).weight(1f),
                                    contentDescription = null
                                )
                            }
                            Text(
                                color = colorResource(R.color.colorPrimary),
                                text = stringResource(R.string.next_visit).capitalize() + ": " + "${SimpleDateFormat("dd/MM/yyyy").format(nextVisit)}",
                                style = MaterialTheme.typography.body2,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(2.dp, 16.dp)
                                    .weight(2f)
                            )
                            if (formatStatus(visit.status) == stringResource(R.string.normopeso)) {
                                Text(
                                    color = colorResource(R.color.colorAccent),
                                    text = "${formatStatus(visit.status)}".toString().capitalize(),
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .padding(2.dp, 16.dp)
                                        .weight(1f)
                                )
                            } else if (formatStatus(visit.status) == stringResource(R.string.objetive_weight)) {
                                Text(
                                    color = colorResource(R.color.colorPrimary),
                                    text = "${formatStatus(visit.status)}".toString().capitalize(),
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .padding(2.dp, 16.dp)
                                        .weight(1f)
                                )
                            } else if (formatStatus(visit.status) == stringResource(R.string.aguda_moderada)) {
                                Text(
                                    color = colorResource(R.color.orange),
                                    text = "MAM".toString().capitalize(),
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .padding(2.dp, 16.dp)
                                        .weight(1f)
                                )
                            } else {
                                Text(
                                    color = colorResource(R.color.error),
                                    text = "MAS".toString().capitalize(),
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .padding(2.dp, 16.dp)
                                        .weight(1f)
                                )

                            }

                            Text(
                                color = colorResource(R.color.frutorial_title),
                                text = stringResource(R.string.visitas).capitalize() + ": " +item.visitsCuadrant.size.toString(),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .padding(0.dp, 16.dp)
                                    .weight(1f)
                            )
                        }

                    }

                }
            }
        }
    } else {
        Spacer(modifier = Modifier.height(0.dp))
    }

}