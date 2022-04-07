package me.asiimwedismas.kmega_mono.module_bakery.presentation.salesman

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.util.Pair
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.asiimwedismas.kmega_mono.ui.common_components.DateRangePickerDialog
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodDeficitsReport(
    onNavigationIconClick: () -> Unit,
    viewModel: PeriodDeficitsViewModel = hiltViewModel(),
) {
    val showCalendar by viewModel.showCalendar
    val isMakingReport by viewModel.makingReport
    val reportRange by viewModel.searchRange
    val reportList by viewModel.reportList

    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigationIconClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "home"
                        )
                    }

                },
                title = { Text(text = reportRange) },
                actions = {
                    IconButton(onClick = viewModel::toggleShowCalendar
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = "Select search range"
                        )
                    }

                }
            )
        },

        ) {
        if (showCalendar) {
            val now = viewModel.dates.instance.value!!.timeInMillis
            DateRangePickerDialog(
                currentSelectedRange = Pair(now, now),
                onRangeSelected = viewModel::handleRangeSelected,
                onDismiss = viewModel::toggleShowCalendar
            )
        }
        val swipeRefreshState = rememberSwipeRefreshState(isMakingReport)
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::makeReport
        ) {
            val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
            LazyColumn(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                items(
                    items = reportList,
                    key = { it.name }
                ) { item ->
                    Column(Modifier.fillMaxWidth()) {
                        ReportItemHolder(numberFormat, item)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportItemHolder(numberFormat: NumberFormat, item: SalesmanDeficitReport) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                contentDescription = "")
        }
    }
    if (isExpanded) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "Debit sales = ${numberFormat.format(item.debit_sales)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Debits paid = ${numberFormat.format(item.debit_paid)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Shortages = ${numberFormat.format(item.shortages)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "excesses = ${numberFormat.format(item.excesses)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
    Divider()
}
