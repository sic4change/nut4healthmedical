package org.sic4change.nut4healthcentrotratamiento.ui.screens.settings


import android.Manifest
import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.*
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.ui.commons.photoselector.camera.CameraCapture
import org.sic4change.nut4healthcentrotratamiento.ui.commons.photoselector.gallery.GallerySelect
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainState

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
            mainState.avatar.value = viewModelState.user!!.photo.toString()
            viewModel.getPoint(viewModelState.user!!.point)
        }
    }

    LaunchedEffect(viewModelState.avatarUrl) {
        if (viewModelState.avatarUrl != null && viewModelState.avatarUrl != "") {
            mainState.avatar.value = viewModelState.avatarUrl
        }
    }

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            mainState.pointId.value = viewModelState.point!!.id
            mainState.point.value = viewModelState.point!!.fullName
            mainState.phoneCode.value = viewModelState.point!!.phoneCode
            mainState.phoneLength.value = viewModelState.point!!.phoneLength
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
                        Box(modifier = Modifier.padding(all = 8.dp)) {
                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .diskCachePolicy(CachePolicy.DISABLED)
                                    .data(mainState.avatar.value)
                                    .size(Size.ORIGINAL)
                                    .build()
                            )
                            if (painter.state is AsyncImagePainter.State.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    color = colorResource(id = R.color.colorPrimary)
                                )
                            } else {
                                Image(
                                    painter = painter,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Profile picture",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                )
                                Canvas(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(size = 31.dp)
                                ) {
                                    drawCircle(
                                        color = Color.Gray
                                    )
                                }
                                Image(
                                    painter = painterResource(R.mipmap.ic_edit_avatar),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            mainState.showPhotoSelector()
                                        }

                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))
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
        SelectPhoto(mainState, mainState.showPhotoSelector.value, viewModel::updateAvatar)
        MessageForgotPassword(mainState.changePass.value, mainState::showChangePassQuestion,
            mainState.email.value, viewModel::changePassword)
        MessageLogout(mainState.logout.value, mainState::showLogoutQuestion, viewModel::logout,
            mainState.pointId.value, onUnSubscribe = { point -> viewModel.unsubscribeToPointNotifications(point) }, onLogout)
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoPermissions(
    mainState: MainState,
    multiplePermissionState: MultiplePermissionsState,
    onSavedAvatar: (imageUri: Uri) -> Unit
) {
    PermissionsRequired(
        multiplePermissionsState = multiplePermissionState,
        permissionsNotGrantedContent = { mainState.showPhotoSelector() },
        permissionsNotAvailableContent = { mainState.showPhotoSelector() }
    ) {
        Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
            var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
            if (imageUri == EMPTY_IMAGE_URI) {
                var showGallerySelect by remember { mutableStateOf(false) }
                if (showGallerySelect) {
                    GallerySelect(
                        onImageUri = { uri ->
                            showGallerySelect = false
                            imageUri = uri
                            mainState.showPhotoSelector()
                            onSavedAvatar(imageUri)
                        }
                    )
                } else {
                    Box(Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
                        CameraCapture(
                            onImageFile = { file ->
                                imageUri = file.toUri()
                                mainState.showPhotoSelector()
                                onSavedAvatar(imageUri)
                            }
                        )
                        IconButton(
                            modifier = Modifier
                                .defaultMinSize(
                                    minWidth = 20.dp,
                                    minHeight = 20.dp
                                )
                                .align(Alignment.TopStart)
                                .padding(4.dp),
                            onClick = {
                                mainState.showPhotoSelector()
                            },
                        ) {
                            Box(modifier = Modifier.padding(16.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_back), "Back",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(32.dp))
                                )
                            }

                        }

                        Box(modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 90.dp).align(Alignment.BottomEnd)) {
                            IconButton(onClick = { showGallerySelect = true },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_gallery),
                                    "Gallery",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(60.dp).background(color = Color.White, shape = RoundedCornerShape(32.dp))
                                        .padding(16.dp)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SelectPhoto(
    mainState: MainState,
    showPhotoSelector: Boolean,
    onSavedAvatar: (imageUri: Uri) -> Unit
) {
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_MEDIA_LOCATION
        )
    )
    if (showPhotoSelector) {
        PhotoPermissions(
            mainState = mainState, multiplePermissionState = multiplePermissionState,
            onSavedAvatar
        )
        multiplePermissionState.launchMultiplePermissionRequest()
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageLogout(showDialog: Boolean, setShowDialog: () -> Unit, onLogout: () -> Unit,
                  point: String, onUnSubscribe: (point:String) -> Unit, onLogoutSelected: () -> Unit) {
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
                        onUnSubscribe(point)
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

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")