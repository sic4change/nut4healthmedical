package org.sic4change.nut4healthcentrotratamiento.ui.commons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.sic4change.nut4healthcentrotratamiento.R
import java.time.LocalDate

@Composable
fun CustomDatePickerDialog(
    value: String,
    onAccept: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }){
        DatePickerUI(value, onAccept)
    }
}

@Composable
fun DatePickerUI(
    value: String,
    onAccept: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        backgroundColor = Color.White,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            val chosenYear = remember { mutableStateOf(value.split("/")[2].toInt()) }
            val chosenMonth = remember { mutableStateOf(value.split("/")[1].toInt()) }
            val chosenDay = remember { mutableStateOf(value.split("/")[0].toInt()) }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.day),
                    style = MaterialTheme.typography.h5,
                     color = colorResource(R.color.colorPrimary),
                    textAlign = TextAlign.Center
                )

                Text(
                    stringResource(R.string.month),
                    style = MaterialTheme.typography.h5,
                    color = colorResource(R.color.colorPrimary),
                    textAlign = TextAlign.Center
                )

                Text(
                    stringResource(R.string.year),
                    style = MaterialTheme.typography.h5,
                    color = colorResource(R.color.colorPrimary),
                    textAlign = TextAlign.Center
                )
            }

            DateSelectionSection(
                value = value,
                onYearChosen = { chosenYear.value = it.toInt() },
                onMonthChosen = { chosenMonth.value = it.toInt()},
                onDayChosen = { chosenDay.value = it.toInt() },
            )


            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                Button(
                    modifier = Modifier
                        .fillMaxWidth().padding(4.dp),
                    onClick = {
                        onAccept("${chosenDay.value}-${chosenMonth.value}-${chosenYear.value}")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                ) {
                    Text("Ok", color = colorResource(R.color.white))
                }
            }
        }
    }
}

@Composable
fun DateSelectionSection(
    value: String,
    onYearChosen: (String) -> Unit,
    onMonthChosen: (String) -> Unit,
    onDayChosen: (String) -> Unit,
) {
    val today = LocalDate.now()
    val currentYear = today.year
    val currentMonth = today.monthValue
    val currentDay = today.dayOfMonth


    val years = remember { (1950..currentYear).map { it.toString() } }
    val monthsNumber = remember(currentYear) { (1.. 12).map { it.toString() } }

    var lastYear by remember { mutableStateOf(currentYear) }
    var lastMonth by remember { mutableStateOf(currentMonth) }

    val days = remember(lastMonth, lastYear) {
        val maxDay = when (lastMonth) {
            4, 6, 9, 11 -> 30
            2 -> if (lastYear % 4 == 0 && (lastYear % 100 != 0 || lastYear % 400 == 0)) 29 else 28
            else -> 31
        }
        (1..if (lastYear == today.year && lastMonth == today.monthValue) currentDay else maxDay).map { it.toString() }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {

        InfiniteItemsPicker(
            items = days,
            firstIndex = value.split("/")[0].toInt(),
            onItemSelected = onDayChosen
        )

        InfiniteItemsPicker(
            items = monthsNumber,
            firstIndex = Int.MAX_VALUE / 2 - 4 + (value.split("/")[1].toInt() - 1),
            onItemSelected = { month ->
                lastMonth = month.toInt()
                onMonthChosen(month)
            }
        )

        InfiniteItemsPicker(
            items = years,
            firstIndex = Int.MAX_VALUE / 2 + (value.split("/")[2].toInt()),
            onItemSelected = { year ->
                lastYear = year.toInt()
                onYearChosen(year)
            }
        )
    }
}

@Composable
fun InfiniteItemsPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    firstIndex: Int,
    onItemSelected: (String) -> Unit,
) {

    val listState = rememberLazyListState(firstIndex)
    val currentValue = remember { mutableStateOf("") }

    LaunchedEffect(key1 = !listState.isScrollInProgress) {
        onItemSelected(currentValue.value)
        listState.animateScrollToItem(index = listState.firstVisibleItemIndex)
    }

    Box(modifier = Modifier.height(106.dp)) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            content = {
                items(count = Int.MAX_VALUE, itemContent = {
                    val index = it % items.size
                    if (it == listState.firstVisibleItemIndex + 1) {
                        currentValue.value = items[index]
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = items[index],
                        modifier = Modifier.alpha(if (it == listState.firstVisibleItemIndex + 1) 1f else 0.3f),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                })
            }
        )
    }
}

