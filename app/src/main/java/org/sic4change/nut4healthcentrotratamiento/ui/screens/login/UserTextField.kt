package org.sic4change.nut4healthcentrotratamiento.ui.screens.login

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun UserTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(R.color.white),
            cursorColor = colorResource(R.color.colorAccent),
            disabledLabelColor =  Color.LightGray,
            focusedIndicatorColor = colorResource(R.color.colorAccent),
            unfocusedIndicatorColor = Color.LightGray,
        ),
        label = { Text(stringResource(R.string.email)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = keyboardActions,
        modifier = Modifier.focusRequester(focusRequester)
    )
}