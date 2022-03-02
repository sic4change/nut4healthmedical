package org.sic4change.nut4healthcentrotratamiento.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.sic4change.nut4healthcentrotratamiento.R

val Bariol = FontFamily(
    Font(R.font.bariol_regular, FontWeight.Black, FontStyle.Normal),
    Font(R.font.bariol_bold, FontWeight.Bold, FontStyle.Normal)

)

// Set of Material typography styles to start with
val Typography = Typography(

    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    defaultFontFamily = Bariol,
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

)