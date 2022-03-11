package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Header()
            }
            /*item.references.forEach {
                val (icon, @StringRes stringRes) = it.type.createUiData()
                section(icon, stringRes, it.references)
            }*/
        }

    }

}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header() {



    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Childs")

    }
}


