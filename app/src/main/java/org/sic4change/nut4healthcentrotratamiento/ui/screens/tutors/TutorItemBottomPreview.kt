package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import arrow.typeclasses.Monoid.Companion.string
import coil.compose.rememberImagePainter
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun TutorItemBottomPreview(item: Tutor?, onGoToDetail: (Tutor) -> Unit) {
    if (item != null) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(null),
                contentDescription = item.name,
                modifier = Modifier
                    .width(96.dp)
                    .aspectRatio(1 / 1.5f)
                    .background(Color.LightGray)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = item.name, style = MaterialTheme.typography.h6)
                Text(text = item.surnames)
                Button(
                    onClick = { onGoToDetail(item) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.go_to_detail))
                }
            }
        }
        Text(text = item.name)
    } else {
        Spacer(modifier = Modifier.height(1.dp))
    }

}
