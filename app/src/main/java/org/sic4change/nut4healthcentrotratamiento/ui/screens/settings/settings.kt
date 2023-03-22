package org.sic4change.nut4healthcentrotratamiento.ui.screens.settings


import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.*
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState

@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun SettingsScreen(viewModel: MainViewModel = viewModel(), onLogout: () -> Unit) {
    val mainState = rememberMainState()
    val viewModelState by viewModel.state.collectAsState()
    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(viewModelState.user) {
        if (viewModelState.user != null) {
            mainState.id.value = viewModelState.user!!.id
            mainState.role.value = viewModelState.user!!.role
            mainState.email.value = viewModelState.user!!.email
            mainState.username.value = viewModelState.user!!.username
            viewModel.getPoint(viewModelState.user!!.point)
        }
    }

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            mainState.point.value = viewModelState.point!!.fullName
        }
    }

    BackHandler {
        activity?.finish()
    }

    NUT4HealthScreen {
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

                Card {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {

                        AnimatedVisibility(visible = (mainState.email.value!= null)) {
                            TextField(value = mainState.username.value,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                                ),
                                enabled = false,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h6,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Sentences),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp, 0.dp),
                                leadingIcon = {
                                    Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                                label = { Text(stringResource(R.string.username), color = colorResource(R.color.disabled_color)) })

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        AnimatedVisibility(visible = (mainState.email.value!= null)) {
                                TextField(value = mainState.email.value,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = colorResource(R.color.colorPrimary),
                                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        cursorColor = colorResource(R.color.colorAccent),
                                        disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        focusedIndicatorColor = colorResource(R.color.colorAccent),
                                        unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                                    ),
                                    enabled = false,
                                    onValueChange = {},
                                    textStyle = MaterialTheme.typography.h6,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        capitalization = KeyboardCapitalization.Sentences),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp, 0.dp),
                                    leadingIcon = {
                                        Icon(Icons.Filled.Email, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                                    label = { Text(stringResource(R.string.username), color = colorResource(R.color.disabled_color)) })
                                Spacer(modifier = Modifier.height(8.dp))
                        }

                        AnimatedVisibility(visible = (mainState.email.value!= null)) {
                                TextField(value = mainState.role.value,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = colorResource(R.color.colorPrimary),
                                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        cursorColor = colorResource(R.color.colorAccent),
                                        disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        focusedIndicatorColor = colorResource(R.color.colorAccent),
                                        unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                                    ),
                                    enabled = false,
                                    onValueChange = {},
                                    textStyle = MaterialTheme.typography.h6,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        capitalization = KeyboardCapitalization.Sentences
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp, 0.dp),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.VerifiedUser,
                                            null,
                                            tint = colorResource(R.color.colorPrimary),
                                            modifier = Modifier.clickable { /* .. */ })
                                    },
                                    label = {
                                        Text(
                                            stringResource(R.string.username),
                                            color = colorResource(R.color.disabled_color)
                                        )
                                    })
                                Spacer(modifier = Modifier.height(8.dp))
                        }

                        AnimatedVisibility(visible = (mainState.point.value!= null)) {
                                TextField(value = mainState.point.value,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = colorResource(R.color.colorPrimary),
                                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        cursorColor = colorResource(R.color.colorAccent),
                                        disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        focusedIndicatorColor = colorResource(R.color.colorAccent),
                                        unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                                    ),
                                    enabled = false,
                                    onValueChange = {},
                                    textStyle = MaterialTheme.typography.h6,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        capitalization = KeyboardCapitalization.Sentences
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp, 0.dp),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.LocalHospital,
                                            null,
                                            tint = colorResource(R.color.colorPrimary),
                                            modifier = Modifier.clickable { /* .. */ })
                                    },
                                    label = {
                                        Text(
                                            stringResource(R.string.username),
                                            color = colorResource(R.color.disabled_color)
                                        )
                                    })
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                            onClick = { mainState.showChangePassQuestion() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 0.dp),
                        ) {
                            Text(stringResource(R.string.change_password), color = colorResource(R.color.white), style = MaterialTheme.typography.h6)
                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                            onClick = { mainState.showLogoutQuestion() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 0.dp),
                        ) {
                            Text(stringResource(R.string.logout), color = colorResource(R.color.white), style = MaterialTheme.typography.h6)
                        }

                    }
                }
            }

        }
        MessageForgotPassword(mainState.changePass.value, mainState::showChangePassQuestion, mainState.email.value, viewModel::changePassword)
        MessageLogout(mainState.logout.value, mainState::showLogoutQuestion, viewModel::logout, onLogout)
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageChangePassword(showDialog: Boolean, setShowDialog: () -> Unit, email: String, onChangePassword: (String) -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(stringResource(R.string.nut4health))
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                        onChangePassword(email)
                    },
                ) {
                    Text(stringResource(R.string.accept), color = colorResource(R.color.white))
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                    },
                ) {
                    Text(stringResource(R.string.close),color = colorResource(R.color.white))
                }
            },
            text = {
                Text(stringResource(R.string.forgot_password_question))
            },
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageLogout(showDialog: Boolean, setShowDialog: () -> Unit, onLogout: () -> Unit, onLogoutSelected: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(stringResource(R.string.nut4health))
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                        onLogout()
                        onLogoutSelected()
                    },
                ) {
                    Text(stringResource(R.string.accept), color = colorResource(R.color.white))
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                    },
                ) {
                    Text(stringResource(R.string.cancel),color = colorResource(R.color.white))
                }
            },
            text = {
                Text(stringResource(R.string.logout_question))
            },
        )
    }

}