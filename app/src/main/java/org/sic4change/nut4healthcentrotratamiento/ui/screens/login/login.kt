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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.*
import org.sic4change.nut4healthcentrotratamiento.R
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), onLogin: () -> Unit) {
    val loginState = rememberLoginState()
    val viewModelState by viewModel.state.collectAsState()
    LaunchedEffect(viewModelState.loggedUser) {
        if (viewModelState.loggedUser) {
            onLogin()
        }
    }
    LaunchedEffect(viewModelState.errorLogin) {
        if (viewModelState.errorLogin.isNotEmpty()) {
            loginState.isError.value = true
            loginState.errorType.value = ErrorType.EMAILORPASS
        }
    }



    LoginForm(loginState, viewModel::loginUser, viewModel::forgotPassword)

}

@ExperimentalComposeUiApi
@Composable
private fun LoginForm(loginState: LoginState, onLogin: (String, String) -> Unit, onForgotPass: (String) -> Unit) {
    NUT4HealthScreen {

        TopView()

        MainView(loginState, onLogin, onForgotPass)

        BottomView()

    }
}

fun validateLogin(user: String, pass: String): String = when {
    !user.contains('@') -> "User must be a valid email"
    pass.length < 5 -> "Pass must be a valid pass"
    else -> ""
}

fun validateForgotPassword(user: String): String = when {
    !user.contains('@') -> "User must be a valid email"
    else -> ""
}

@Composable
fun MainView(loginState: LoginState,
             onLogin: (String, String) -> Unit, onForgotPass: (String) -> Unit) {

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
                        value = loginState.email.value,
                        onValueChange = { loginState.email.value = it }
                    )
                    PassTextField(
                        value = loginState.pass.value,
                        onValueChange = { loginState.pass.value = it }
                    )

                    AnimatedVisibility(visible = (loginState.email.value.length > 3 && loginState.email.value.contains('@'))) {
                        Text(
                            text = stringResource(R.string.forgot_password),
                            style = MaterialTheme.typography.caption,
                            color = Color.LightGray,
                            modifier = Modifier.padding(8.dp).clickable {
                                loginState.forgotPasswordClicked()
                                onForgotPass(loginState.email.value)
                            })
                    }

                    AnimatedVisibility(visible = loginState.isError.value) {
                        Text(
                            text = stringResource(loginState.errorType.value.message),
                            style = MaterialTheme.typography.caption,
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp))
                    }

                    AnimatedVisibility(visible = (loginState.email.value.length > 3 && loginState.email.value.contains('@') && loginState.pass.value.length > 3)) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                            onClick = {
                                loginState.loginClicked()
                                onLogin(loginState.email.value, loginState.pass.value)
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

@Composable
fun TopView() {
    val configuration = LocalConfiguration.current
    val screenHeight = (configuration.screenHeightDp.dp / 3) + 75.dp

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
            Spacer(modifier = Modifier.height(screenHeight))
        }
    }

}

@Composable
fun BottomView() {
    val uriHandler = LocalUriHandler.current
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
                color = Color.White,
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://www.sic4change.org/politica-de-privacidad")
                }
            )
            Text(
                text = "Version ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
        }

    }
}

@Composable
fun showMessageForgotPassword() {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(R.string.nut4health), style = MaterialTheme.typography.h6)
                Text(text = stringResource(R.string.forgot_password_question))
                Button(
                    onClick = { executeForgotPassword() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(R.string.send_email))
                }
            }
        }
        Text(text = stringResource(R.string.nut4health))
    }

fun executeForgotPassword() {
    TODO("Not yet implemented")
}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun LoginDetailScreen(viewModel: LoginDetailViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Congratulations!!", style = MaterialTheme.typography.h3)
    }
}


