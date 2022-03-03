package org.sic4change.nut4healthcentrotratamiento.ui.screens.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.sic4change.nut4healthcentrotratamiento.R

const val PASS_TEXT_FIELD_TEST_TAG = "PassTextFieldTestTag"
const val PASS_REVEAL_ICON_TEST_TAG = "PassRevealIconTestTag"

@Composable
fun PassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    var passVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(R.color.white),
            cursorColor = colorResource(R.color.colorAccent),
            disabledLabelColor =  Color.LightGray,
            focusedIndicatorColor = colorResource(R.color.colorAccent),
            unfocusedIndicatorColor = Color.LightGray,
        ),
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.password)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconToggleButton(
                checked = passVisible,
                onCheckedChange = { passVisible = it },
                modifier = Modifier.testTag(PASS_REVEAL_ICON_TEST_TAG)
            ) {
                Crossfade(targetState = passVisible) { visible ->
                    if (visible) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = stringResource(id = R.string.hide_password)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = stringResource(id = R.string.reveal_password)
                        )
                    }
                }
            }
        },
        keyboardActions = keyboardActions,
        modifier = Modifier.testTag(PASS_TEXT_FIELD_TEST_TAG).focusRequester(focusRequester)
    )
}
