package org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getMonths
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getYears
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DerivationItemCreateScreen(derivationState: DerivationState,
                               loading: Boolean = false,
                               onCreateDerivation: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
                DerivationView(
                    loading = loading,
                    derivationState = derivationState,
                    onCreateDerivation = onCreateDerivation,
                )

        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun DerivationView(loading: Boolean,
                           derivationState: DerivationState,
                           onCreateDerivation: () -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shapeDefault = RoundedCornerShape(8.dp)

    @Composable
    fun getAgeFormatted(child: Child): String {
        if (child == null) {
            return ""
        } else {
            return "${SimpleDateFormat("dd/MM/yyyy").format(child.birthdate)}!≈${getYears(child.birthdate)} ${stringResource(R.string.years)} ${getMonths(child.birthdate)} ${stringResource(R.string.months)}"
        }
    }

    if (!loading) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.form_derivation),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
            item {
                Column {
                    TextField(
                        value = derivationState.currentPointName.value,
                        onValueChange = { derivationState.currentPointName.value = it },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h5.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.black_gray), shape = shapeDefault),
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.mipmap.ic_derivation_centre_blue),
                                contentDescription = null,
                                modifier =  Modifier.size(24.dp)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.current_centre),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = derivationState.currentPointPhone.value,
                        onValueChange = { derivationState.currentPointPhone.value = it },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h5.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.black_gray), shape = shapeDefault),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Phone,
                                contentDescription = null,
                                tint = colorResource(R.color.colorPrimary),
                                modifier = Modifier.clickable {  }
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.phone),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                }
            }
            item {

                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.select_derivation_centre),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(R.mipmap.ic_arrow_down),
                        contentDescription = null,
                        modifier =  Modifier.size(32.dp).align(Alignment.CenterHorizontally)
                    )
                }
            }

            item {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp),
                        expanded = derivationState.expandedDerivationCentre.value,
                        onExpandedChange = {
                            derivationState.expandedDerivationCentre.value = !derivationState.expandedDerivationCentre.value
                        }
                    ) {
                        TextField(
                            value = derivationState.selectedOptionDerivationCentre.value,
                            onValueChange = {
                                derivationState.selectedOptionDerivationCentre.value = it
                            },
                            enabled = false,
                            textStyle = MaterialTheme.typography.h5.copy(color = if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.white)),
                            trailingIcon = {
                                Icon(
                                    imageVector = if (derivationState.expandedDerivationCentre.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                    contentDescription = "Dropdown Icon",
                                    tint = if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.white),
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) colorResource(R.color.full_transparent) else colorResource(R.color.colorPrimary), shape = shapeDefault)
                                .border(1.dp, colorResource(R.color.colorPrimary), shape = shapeDefault),
                            leadingIcon = {
                                Image(
                                    painter = if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) painterResource(R.mipmap.ic_derivation_centre_blue) else painterResource(R.mipmap.ic_derivation_centre_white),
                                    contentDescription = null,
                                    modifier =  Modifier.size(24.dp)
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.white),
                                backgroundColor = if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) colorResource(androidx.browser.R.color.browser_actions_bg_grey) else colorResource(R.color.colorPrimary),
                                cursorColor = colorResource(R.color.full_transparent),
                                disabledTextColor = colorResource(R.color.colorPrimary),
                                disabledIndicatorColor = colorResource(R.color.full_transparent),
                                disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                focusedIndicatorColor = colorResource(R.color.full_transparent),
                                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                            ),
                            label = {
                                Text(
                                    stringResource(R.string.centre_name),
                                    color = if (derivationState.selectedOptionDerivationCentre.value.isEmpty()) colorResource(R.color.disabled_color) else colorResource(R.color.white)
                                )
                            }
                        )
                        ExposedDropdownMenu(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),
                            expanded = derivationState.expandedDerivationCentre.value,
                            onDismissRequest = {
                                derivationState.expandedDerivationCentre.value = false
                            }
                        ) {
                            derivationState.points.value.forEach { selectedCentre ->
                                DropdownMenuItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 0.dp),
                                    onClick = {
                                        derivationState.selectedOptionDerivationCentre.value = selectedCentre.name
                                        derivationState.expandedDerivationCentre.value = false
                                    }
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
                                        text = selectedCentre.name,
                                        color = colorResource(R.color.colorPrimary),
                                    )
                                }
                            }
                        }
                    }

                    TextField(
                        value = derivationState.getPhoneSelectedDerivationCentre(),
                        onValueChange = { derivationState.currentPointPhone.value = it },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h5.copy(color = if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.white)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(androidx.browser.R.color.browser_actions_bg_grey) else colorResource(R.color.colorPrimary), shape = shapeDefault)
                            .border(1.dp, if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.colorPrimary), shape = shapeDefault),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Phone,
                                contentDescription = null,
                                tint = if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.white),
                                modifier = Modifier.clickable {  }
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(R.color.colorPrimary) else colorResource(R.color.white),
                            backgroundColor = if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(androidx.browser.R.color.browser_actions_bg_grey) else colorResource(R.color.colorPrimary),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.phone),
                                color = if (derivationState.getPhoneSelectedDerivationCentre().isEmpty()) colorResource(R.color.disabled_color) else colorResource(R.color.white)
                            )
                        }
                    )

                }
            }

            item {

                Column {

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(color = colorResource(R.color.colorPrimary), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        stringResource(R.string.case_info),
                        color = colorResource(R.color.disabled_color),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = derivationState.getCurrentDate(),
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_date),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = derivationState.child.value?.let { "${it.name} ${it.surnames}" } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_child_name),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = getAgeFormatted(derivationState.child.value!!),
                        visualTransformation = ColorsTransformation(),
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.birthdate),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = derivationState.child.value?.let { it.sex } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.sex),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = derivationState.tutor.value?.let { "${it.name} ${it.surnames}" } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_tutor_name),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = derivationState.tutor.value?.let { it.phone } ?: "",
                        onValueChange = { },
                        enabled = false,
                        textStyle = MaterialTheme.typography.h6.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.full_transparent), shape = shapeDefault)
                            .border(1.dp, colorResource(R.color.full_transparent), shape = shapeDefault),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.full_transparent),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.derivation_tutor_phone),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))



                    Divider(color = colorResource(R.color.colorPrimary), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))

                }

            }

        }
    }
}




class ColorsTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.toString()),
            OffsetMapping.Identity)
    }

    fun buildAnnotatedStringWithColors(text:String): AnnotatedString{
        val words: List<String> = text.split("!")// splits by whitespace
        val colors = listOf(Color(android.graphics.Color.parseColor("#379FF3")), Color(android.graphics.Color.parseColor("#9EC830")))
        var count = 0

        val builder = AnnotatedString.Builder()
        for (word in words) {
            builder.withStyle(style = SpanStyle(color = colors[count%2])) {
                append("$word ")
            }
            count ++
        }
        return builder.toAnnotatedString()
    }
}





