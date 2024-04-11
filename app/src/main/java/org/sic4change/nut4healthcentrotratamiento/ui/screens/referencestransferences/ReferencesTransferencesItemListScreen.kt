package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations.rememberDerivationState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorListItem

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ReferencesTransferencesItemsListScreen(
    type: String,
    loading: Boolean = false,
    items: List<Derivation?>,
    childs: List<Child?>,
    tutors: List<Tutor?>,
    onClick: (Derivation) -> Unit,
    onSearch: (String) -> Unit
) {
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch {
                sheetState.hide()
            }
        }

    if (loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
        }
    }

    ReferencesTransferencesItemsList(
        type = type,
        loading = loading,
        items = items,
        childs = childs,
        tutors = tutors,
        onItemClick = onClick,
        onSearch = onSearch
    )
}



@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ReferencesTransferencesItemsList(
    type: String,
    loading: Boolean,
    items: List<Derivation?>,
    childs: List<Child?>,
    tutors: List<Tutor?>,
    onItemClick: (Derivation) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val referencesTransferencesState = rememberDerivationState()

    Column(
        modifier = Modifier.fillMaxWidth().background(colorResource(R.color.gray_light))
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (type == "Referred") stringResource(R.string.refernce_list) else stringResource(R.string.trasferred_list),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = modifier.fillMaxSize().background(colorResource(R.color.gray_light)),
            contentAlignment = Alignment.TopCenter
        ) {
            if (loading) {
                CircularProgressIndicator(color = colorResource(R.color.colorPrimary), modifier = Modifier.padding(40.dp))
            } else {
                if (items.isNotEmpty()) {
                    Card(
                        shape = RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                        modifier = Modifier.fillMaxSize().padding(top = 16.dp).background(colorResource(R.color.gray_light))
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(top = 32.dp, bottom = 0.dp, end = 0.dp, start = 0.dp),
                            modifier = Modifier.background(colorResource(R.color.gray_light))
                        ) {
                            items(items) {
                                ReferenceTransferenceListItem(
                                    derivation = it!!,
                                    child = childs.find { child -> child?.id == it.childId },
                                    tutor = tutors.find { tutor -> tutor?.id == it.fefaId },
                                    modifier = Modifier.clickable { onItemClick(it) },
                                    onClickDetail = onItemClick
                                )
                            }

                        }
                    }
                } else {
                    Text(
                        text = if (type == "Referred") stringResource(R.string.no_refernce_list) else stringResource(R.string.no_trasferred_list),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }

    }

}