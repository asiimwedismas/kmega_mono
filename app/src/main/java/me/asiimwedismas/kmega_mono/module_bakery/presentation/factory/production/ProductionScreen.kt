package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.asiimwedismas.kmega_mono.ui.common_components.DateRangePickerDialog
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionItem
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.InvoiceType
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AppBar
import me.asiimwedismas.kmega_mono.ui.common_components.DatePickerDialog


@ExperimentalMaterial3Api
@Preview
@Composable
fun ProductionScreen(
    viewModel: ProductionViewModel = hiltViewModel(),
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.pinnedScrollBehavior { false }
    }

    val selectedDate by viewModel.dates.selectedDate.observeAsState("")
    val showCalendar by viewModel.showCalendar
    val itemsList by viewModel.itemsList
    val totalProductionAmount by viewModel.totalFactoryProduction

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            AppBar(
                title = "Production: $selectedDate",
                onNavigationIconClick = {},
                onPreviousDateClick = viewModel::selectPreviousDate,
                onSelectDateClick = viewModel::toggleShowCalendar,
                onNextDateClick = viewModel::selectNextDate,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        if (showCalendar) {
            DatePickerDialog(
                currentSelected = viewModel.dates.instance.value!!.timeInMillis,
                onDateSelected = viewModel::changeDate,
                onDismiss = viewModel::toggleShowCalendar
            )
        }
        Text(text = totalProductionAmount.toString())
        ProductionContent(itemsList = listOf())
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductionContent(
    itemsList: List<FactoryProductionItem>,
) {

    val invoiceType = remember {
        mutableStateOf(InvoiceType.DISPATACH)
    }



    LazyColumn {

        itemsIndexed(
            items = itemsList,
            key = { index, item ->
                "$index+${item.product_name}"
            }
        ) { position, item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
//                        deleteIndex = position
//                        openDialog = true
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = dismissState,
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val color by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> Color.LightGray
                            else -> Color.Red
                        }
                    )

                    val scale by animateFloatAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
                    )

                    val alignment = when (direction) {
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                    }

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = color)
                        .padding(start = 12.dp, end = 12.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.scale(scale))
                    }
                },
                dismissContent = {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value,
                        content = {
//                            InvoiceItemHolder(item = item,
//                                invoiceType = invoiceType.value)
                        }
                    )
                }
            )
        }
    }

}


