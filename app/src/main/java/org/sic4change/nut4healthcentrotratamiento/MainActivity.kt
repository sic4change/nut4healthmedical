package org.sic4change.nut4healthcentrotratamiento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.sic4change.nut4healthcentrotratamiento.ui.theme.LoginSampleTheme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login()
        }
    }
}

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




        BottomView()

        Box(
            contentAlignment = Alignment.Center
        ) {
            Card {
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
                        contentScale = ContentScale.Crop,            // crop the image if it's not a square
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .border(0.dp, colorResource(R.color.colorPrimary), CircleShape)   // add a border (optional)
                    )

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

}

fun validateLogin(user: String, pass: String): String = when {
    !user.contains('@') -> "User must be a valid email"
    pass.length < 5 -> "Password must have at least 5 characters"
    else -> ""
}

@Composable
fun Screen(content: @Composable () -> Unit) {
    LoginSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            content = content
        )
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





