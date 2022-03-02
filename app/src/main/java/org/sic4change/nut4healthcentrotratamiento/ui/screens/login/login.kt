package org.sic4change.nut4healthcentrotratamiento.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import org.sic4change.nut4healthcentrotratamiento.*
import org.sic4change.nut4healthcentrotratamiento.R

@ExperimentalAnimationApi
@Composable
fun Login() {
    Screen {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }

        val loginEnabled = email.isNotEmpty() && pass.isNotEmpty()
        val forgotPasswordEnabled = email.isNotEmpty()

        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        TopView(height = (screenHeight/3) + 75.dp)


        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(R.mipmap.icon),
                    contentDescription = "nut4health",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.colorPrimary))
                        .border(8.dp, colorResource(R.color.white), CircleShape)
                )
                Card {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {
                        UserTextField(
                            value = email,
                            onValueChange = { email = it }
                        )
                        PassTextField(
                            value = pass,
                            onValueChange = { pass = it }
                        )

                        AnimatedVisibility(visible = forgotPasswordEnabled) {
                            Text(
                                text = stringResource(R.string.forgot_password),
                                style = MaterialTheme.typography.caption,
                                color = Color.LightGray,
                                modifier = Modifier.padding(8.dp).clickable {
                                })
                        }

                        AnimatedVisibility(visible = loginEnabled) {
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                                onClick = {

                                }
                            ) {
                                Text(text = stringResource(R.string.login), color = colorResource(R.color.white))
                            }
                        }

                    }
                }
            }

        }

        BottomView()

    }

}


fun validateLogin(user: String, pass: String): String = when {
    !user.contains('@') -> "User must be a valid email"
    pass.length < 5 -> "Password must have at least 5 characters"
    else -> ""
}

@Composable
fun TopView(height: Dp) {
    Box(
        contentAlignment = Alignment.TopCenter,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp, 0.dp, 300.dp, 100.dp))
                .background(colorResource(R.color.colorPrimary))
                .padding(8.dp)
        ) {

            Spacer(modifier = Modifier.height(height))

        }

    }

}

@Composable
fun BottomView() {
    Box(
        contentAlignment = Alignment.BottomCenter,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100.dp, 100.dp, 0.dp, 0.dp))
                .background(colorResource(R.color.colorPrimary))
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.terms_and_conditions),
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                text = "Version ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
        }

    }
}

