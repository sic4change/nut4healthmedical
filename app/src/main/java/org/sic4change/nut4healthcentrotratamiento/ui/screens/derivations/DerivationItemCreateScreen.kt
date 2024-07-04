package org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getMonths
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getYears
import java.text.SimpleDateFormat
import java.util.*
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.commons.StringResourcesUtil.Companion.doesStringMatchAnyLocale
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getMonthsAgo
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.ItemViewIcon
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.SteptTitle

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DerivationItemCreateScreen(derivationState: DerivationState,
                               loading: Boolean = false,
                               onCreateDerivation: (Derivation, String) -> Unit,
                               onCreateDerivationSucessfull: (String) -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DerivationView(
                    loading = loading,
                    derivationState = derivationState,
                    onCreateDerivation = onCreateDerivation,
                    onCreateDerivationSucessfull = onCreateDerivationSucessfull
                )

            }
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
                           onCreateDerivation: (Derivation, String) -> Unit,
                           onCreateDerivationSucessfull: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shapeDefault = RoundedCornerShape(8.dp)

    @Composable
    fun getAgeChildFormatted(child: Child): String {
        if (child == null) {
            return ""
        } else {
            return "${SimpleDateFormat("dd/MM/yyyy").format(child.birthdate)}!≈${getYears(child.birthdate)} ${stringResource(R.string.years)} ${getMonths(child.birthdate)} ${stringResource(R.string.months)}"
        }
    }

    @Composable
    fun getAgeFEFAFormatted(fefa: Tutor): String {
        if (fefa == null) {
            return ""
        } else {
            return "${SimpleDateFormat("dd/MM/yyyy").format(fefa.birthdate)}!≈${getYears(fefa.birthdate)} ${stringResource(R.string.years)} ${getMonths(fefa.birthdate)} ${stringResource(R.string.months)}"
        }
    }

    if (!loading) {
        if (derivationState.showConfirmationDialog.value) {
            SuccessDialog(
                type = derivationState.type.value,
                onDismiss = { onCreateDerivationSucessfull(derivationState.case.value!!.id) },
                code = derivationState.getCode())
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        if (derivationState.type.value == "Referred") stringResource(R.string.form_refence) else stringResource(R.string.form_transference),
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
                        if (derivationState.type.value == "Referred") stringResource(R.string.select_reference_centre) else stringResource(R.string.select_transference_centre),
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

                    if (derivationState.getIdSelectedDerivationCentre().isNotEmpty()) {
                        TextField(
                            value = derivationState.getCode(),
                            onValueChange = {  },
                            enabled = false,
                            textStyle = MaterialTheme.typography.body1.copy(color = colorResource(R.color.colorPrimary)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(colorResource(R.color.white), shape = shapeDefault)
                                .border(1.dp, colorResource(R.color.colorPrimary), shape = shapeDefault),
                            leadingIcon = {
                                Image(
                                    painter = painterResource(R.mipmap.ic_ref_derivation_form),
                                    contentDescription = null,
                                    modifier = Modifier.padding(4.dp).size(40.dp)
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = colorResource(R.color.colorPrimary),
                                backgroundColor = colorResource(R.color.white),
                                cursorColor = colorResource(R.color.full_transparent),
                                disabledTextColor = colorResource(R.color.colorPrimary),
                                disabledIndicatorColor = colorResource(R.color.full_transparent),
                                disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                focusedIndicatorColor = colorResource(R.color.full_transparent),
                                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                            ),
                            label = {
                                Text(
                                    if (derivationState.type.value == "Referred") stringResource(R.string.reference_code) else stringResource(R.string.transference_code),
                                    color = colorResource(R.color.colorPrimary)
                                )
                            }
                        )
                    }

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
                                if (derivationState.type.value == "Referred") stringResource(R.string.reference_date) else stringResource(R.string.transference_date),
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
                        value = if (derivationState.child.value != null) getAgeChildFormatted(derivationState.child.value!!) else getAgeFEFAFormatted(derivationState.tutor.value!!),
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

                    TextField(
                        value = derivationState.getAdmissionDate(),
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
                                stringResource(R.string.admission_date),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                }

            }

            item {

                Column {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        if (derivationState.type.value == "Referred") stringResource(R.string.antropometric_reference_date) else stringResource(R.string.antropometric_transference_date),
                        color = colorResource(R.color.disabled_color),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )

                    Column(horizontalAlignment = Alignment.Start) {
                        if (derivationState.currentPointType.value == "CRENAM") {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Box(modifier = Modifier.weight(1f)) {
                                    TextField(
                                        value = derivationState.getWeight(),
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
                                                stringResource(R.string.weight),
                                                color = colorResource(R.color.disabled_color)
                                            )
                                        }
                                    )
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    TextField(
                                        value = derivationState.getHeight(),
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
                                                stringResource(R.string.height),
                                                color = colorResource(R.color.disabled_color)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            if (derivationState.currentPointType.value == "CRENAM") {
                                Box(modifier = Modifier.weight(1f)) {
                                    TextField(
                                        value = derivationState.getIMC(),
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
                                            Text("P/T (Z-score)",
                                                color = colorResource(R.color.disabled_color)
                                            )
                                        }
                                    )
                                }
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                TextField(
                                    value = derivationState.getArmCircunference(),
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
                                        Text(stringResource(R.string.arm_circunference),
                                            color = colorResource(R.color.disabled_color)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    TextField(
                        value = derivationState.lastVisit.value?.let { it.edema } ?: "",
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
                                stringResource(R.string.edema),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    TextField(
                        value = derivationState.getComplications(),
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
                                stringResource(R.string.complications),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

            item {

                if (derivationState.visits.value.isNotEmpty() && derivationState.visits.value.size > 1) {
                    Column {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            stringResource(R.string.step3_title),
                            color = colorResource(R.color.disabled_color),
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                        )

                        Column(horizontalAlignment = Alignment.Start) {
                            var i = 1
                            for (visit in derivationState.visits.value.subList(1, derivationState.visits.value.size)) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(stringResource(R.string.visit) + " $i",
                                    color = colorResource(R.color.disabled_color),
                                    style = MaterialTheme.typography.caption,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                                )
                                SystemicTreatmentView(derivationState.child.value!!, visit, i)
                                i++
                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))


                    }
                }


            }

            item {

                Column {

                    Divider(color = colorResource(R.color.colorPrimary), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    if (derivationState.type.value == "Referred") {
                        Text(stringResource(R.string.reference_reason),
                            color = colorResource(R.color.colorPrimary),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    CurrenStatusView(derivationState = derivationState)

                    Spacer(modifier = Modifier.height(8.dp))

                    if (derivationState.getIdSelectedDerivationCentre().isNotEmpty()) {
                        val closeText = stringResource(R.string.close)
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),

                            onClick = {
                                val id = "${derivationState.pointId.value}_${derivationState.getIdSelectedDerivationCentre()}_${derivationState.child.value?.id}_${derivationState.tutor.value?.id}"
                                val derivation = Derivation(id, derivationState.type.value, derivationState.case.value!!.id, derivationState.pointId.value, derivationState.getIdSelectedDerivationCentre(),
                                    derivationState.child.value?.id, derivationState.tutor.value?.id, Date(), derivationState.getCode(), true)
                                onCreateDerivation(derivation, closeText)
                            },
                        ) {
                            Text(text = stringResource(R.string.save_derivation_form), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    }
}


@ExperimentalCoilApi
@Composable
fun CurrenStatusView(
    derivationState: DerivationState,
    modifier: Modifier = Modifier
) {
    var colorBackground : Color = Color.White
    if (doesStringMatchAnyLocale(LocalContext.current, "normopeso", formatStatus(derivationState.lastVisit.value!!.status))) {
        colorBackground = colorResource(R.color.colorAccent)
    } else if (doesStringMatchAnyLocale(LocalContext.current, "objetive_weight", formatStatus(derivationState.lastVisit.value!!.status))) {
        colorBackground = colorResource(R.color.colorPrimary)
    } else if (doesStringMatchAnyLocale(LocalContext.current, "aguda_moderada", formatStatus(derivationState.lastVisit.value!!.status))) {
        colorBackground = colorResource(R.color.orange)
    } else {
        colorBackground = colorResource(R.color.error)
    }
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card(
            backgroundColor = colorBackground,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        tint = colorResource(R.color.white),
                        imageVector = Icons.Default.Folder,
                        contentDescription = null
                    )
                }
                Text(
                    color = colorResource(R.color.white),
                    text = "${formatStatus(derivationState.lastVisit.value!!.status) }".toString().capitalize()  ,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(8.dp, 16.dp)
                        .weight(1f)
                )

            }
        }
    }
}

@Composable
fun SystemicTreatmentView(child: Child, visit: Visit, visitNumber: Int) {

    val monthsBetween = getMonthsAgo(child.birthdate.time)

    AnimatedVisibility(visit.status.isNotEmpty()
            && doesStringMatchAnyLocale(LocalContext.current, "aguda_moderada", visit.status)
            && visitNumber == 1) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp, 0.dp)) {
            Text(stringResource(R.string.vitamine_a_title), color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
        }

    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && doesStringMatchAnyLocale(LocalContext.current, "aguda_moderada", visit.status)
            && visitNumber == 0) {
        if ((monthsBetween >= 12 && monthsBetween < 24) || (monthsBetween >= 24)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp, 0.dp)) {
                Text(stringResource(R.string.albendazole_a_title), color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && (doesStringMatchAnyLocale(LocalContext.current, "aguda_moderada", visit.status)
            && visitNumber == 1) || (doesStringMatchAnyLocale(LocalContext.current, "aguda_severa", visit.status)
            && visitNumber == 2)) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp, 0.dp)
        ) {
            if ((monthsBetween >= 12 && monthsBetween < 24)) {
                Text(stringResource(R.string.abendazol_400_half) + "/" + stringResource(R.string.mebendazol_400_half),
                    color = colorResource(R.color.colorPrimary)
                )
            } else if ((monthsBetween >= 24)) {
                Text(stringResource(R.string.abendazol_400_full) + "/" + stringResource(R.string.mebendazol_400_full),
                    color = colorResource(R.color.colorPrimary)
                )
            }
        }
    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && doesStringMatchAnyLocale(LocalContext.current, "aguda_moderada", visit.status) ) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp, 0.dp)) {
            Text(stringResource(R.string.ferro_title), color = colorResource(R.color.colorPrimary), style = MaterialTheme.typography.h5)
        }

    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && doesStringMatchAnyLocale(LocalContext.current, "aguda_moderada", visit.status) ) {
        Column {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp, 0.dp)
            ) {
                if ((visit.weight > 0.0 && visit.weight < 10.0)) {
                    Text(stringResource(R.string.capsules_hierro_folico_one), color = colorResource(R.color.colorPrimary)
                    )
                } else {
                    Text(stringResource(R.string.capsules_hierro_folico_tow), color = colorResource(R.color.colorPrimary))
                }
            }
        }
    }

    AnimatedVisibility(visit.status.isNotEmpty()
            && doesStringMatchAnyLocale(LocalContext.current, "aguda_severa", visit.status)
            && visitNumber == 1) {
        Column(
        ) {
            Column(modifier = Modifier
                .wrapContentSize()
                .padding(16.dp, 0.dp)){
                Text(stringResource(R.string.amoxicilina).split(": ")[0].trim(), color = colorResource(R.color.colorPrimary))
                if ((visit.weight > 0.0 && visit.weight < 5.0)) {
                    Text(stringResource(R.string.amoxicilina_125), color = colorResource(R.color.colorPrimary))
                } else if ((visit.weight > 0.0 && visit.weight >= 5.0 && visit.weight < 10.0)) {
                    Text(stringResource(R.string.amoxicilina_250), color = colorResource(R.color.colorPrimary))
                } else if ((visit.weight > 0.0 && visit.weight >= 10.0 && visit.weight < 20.0)) {
                    Text(stringResource(R.string.amoxicilina_500), color = colorResource(R.color.colorPrimary))
                } else if ((visit.weight > 0.0 && visit.weight >= 20.0 && visit.weight < 35.0)) {
                    Text(stringResource(R.string.amoxicilina_750), color = colorResource(R.color.colorPrimary))
                } else {
                    Text(stringResource(R.string.amoxicilina_1000), color = colorResource(R.color.colorPrimary))
                }
            }

            if (visit.amoxicilina.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                ItemViewIcon(visit.amoxicilina, stringResource(R.string.another_tratements), Icons.Filled.Medication)
            }

            if (visit.otherTratments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                ItemViewIcon(visit.otherTratments, stringResource(R.string.another_tratements), Icons.Filled.Medication)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    AnimatedVisibility(visit.vitamineAVaccinated == stringArrayResource(id = R.array.yesnooptions)[2]) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp, 0.dp)
        ) {
            if ((monthsBetween in 6..11) || (visit.weight > 0.0 && visit.weight >= 6.0 && visit.weight <= 8.0)) {
                Text(stringResource(
                    R.string.vitamine_blue),
                    color = colorResource(R.color.colorPrimary)
                )
            } else if ((monthsBetween >= 12) || (visit.weight > 0.0 && visit.weight > 8.0)) {
                Text(stringResource(
                    R.string.vitamine_red),
                    color = colorResource(R.color.colorPrimary)
                )
            }
        }
    }

}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(R.mipmap.ic_ref_derivation_form),
        contentDescription = null,
        modifier =  modifier
    )
}

@Composable
fun SuccessDialog(
    type: String,
    code: String ="Code",
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(370.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .background(
                            color = colorResource(R.color.white),
                            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            if (type == "Referred") stringResource(R.string.reference_succesful) else stringResource(R.string.transference_succesful),
                            color = colorResource(R.color.disabled_color),
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            if (type == "Referred") stringResource(R.string.reference_code).uppercase(Locale.getDefault()) else stringResource(R.string.transference_code).uppercase(Locale.getDefault()),
                            color = colorResource(R.color.black_gray),
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center)

                        Text(
                            code.uppercase(Locale.getDefault()),
                            color = colorResource(R.color.error),
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth().clickable {
                                onDismiss()
                            }
                        ) {
                            Image(
                                painter = painterResource(R.mipmap.ic_check_derivation_form),
                                contentDescription = null,
                                modifier =  Modifier.size(72.dp)
                            )
                            Text(
                                stringResource(R.string.go_back),
                                color = colorResource(R.color.black_gray),
                                fontSize = MaterialTheme.typography.caption.fontSize,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center)
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
            )
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





