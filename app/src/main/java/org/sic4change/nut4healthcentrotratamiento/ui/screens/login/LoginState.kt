package org.sic4change.nut4healthcentrotratamiento.ui.screens.login

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusRequester
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun rememberLoginState(
    scrollState: ScrollState = rememberScrollState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    email: MutableState<String> = rememberSaveable { mutableStateOf("") },
    pass: MutableState<String> = rememberSaveable { mutableStateOf("") },
    showPass: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) },
    isError: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    errorType: MutableState<ErrorType> = remember { mutableStateOf(ErrorType.NONE) },
    logIn: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ LoginState(
    scrollState,
    focusRequester,
    email,
    pass,
    showPass,
    isError,
    errorType,
    logIn) }

class LoginState(
    val scrollState: ScrollState,
    val focusRequester: FocusRequester,
    val email: MutableState<String>,
    val pass: MutableState<String>,
    val showPass: MutableState<Boolean>,
    val isError: MutableState<Boolean>,
    val errorType: MutableState<ErrorType>,
    val logIn: MutableState<Boolean>
) {


    fun loginClicked() = when {
        email.value.length < 3 || !email.value.contains('@') -> {
            isError.value = true
            errorType.value = ErrorType.EMAIL
        }
        pass.value.length < 6 -> {
            isError.value = true
            errorType.value = ErrorType.PASSWORD
        }
        else -> {
            isError.value = false
            logIn.value = true
            errorType.value = ErrorType.NONE
        }
    }

}

enum class ErrorType(val message: Int) {
    NONE(-1),
    EMAIL(R.string.valid_email),
    PASSWORD(R.string.valid_pass),
}