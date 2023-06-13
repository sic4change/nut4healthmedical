package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
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
fun TutorSummaryItem(
    item: Tutor,
    expanded: Boolean = false,
    onExpandDetail : (Tutor) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
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
                        .padding(12.dp, 12.dp)
                )
                Column( modifier = Modifier
                    .weight(1f)) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        color = colorResource(R.color.colorPrimary),
                        text = "${item.name} ${item.surnames}"  ,
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        color = colorResource(R.color.disabled_color),
                        text = item.address ,
                        style = MaterialTheme.typography.caption,
                        maxLines = 2,
                    )

                }

                IconButton(onClick = { onExpandDetail(item) }) {
                    Icon(
                        tint = colorResource(R.color.colorPrimary),
                        imageVector = if (!expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                        contentDescription = stringResource(R.string.more_actions)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = colorResource(androidx.browser.R.color.browser_actions_bg_grey), thickness = 1.dp)



    }
}