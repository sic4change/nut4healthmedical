package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun SearchByPhoneDialog(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>,
    phoneCode: MutableState<String>,
    phoneLength: MutableState<Int>,
    onCheckTutor: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.check_tutor),
                color = colorResource(R.color.colorPrimary),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            TextField(
                value = editMessage.value,
                onValueChange = {
                                    if (it.length <= (phoneLength.value))    {
                                        editMessage.value = it
                                    }
                                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                visualTransformation = PrefixTransformation("+${phoneCode.value}"),
                leadingIcon = { Icon(Icons.Filled.Search, null, tint = colorResource(R.color.disabled_color)) },
                textStyle = MaterialTheme.typography.h5,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    openDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
            ) {
                Text(stringResource(R.string.cancel), color = colorResource(R.color.white))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                enabled = editMessage.value.length == phoneLength.value,
                onClick = {
                    message.value = "${editMessage.value}"
                    openDialog.value = false
                    onCheckTutor("${phoneCode.value}${editMessage.value}" )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
            ) {
                Text(stringResource(R.string.accept), color = colorResource(R.color.white))
            }
        }
    }
}

class PrefixTransformation(val prefix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return PrefixFilter(text, prefix)
    }
}

fun PrefixFilter(number: AnnotatedString, prefix: String): TransformedText {

    var out = prefix + number.text
    val prefixOffset = prefix.length

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < prefixOffset) return 0
            return offset - prefixOffset
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}