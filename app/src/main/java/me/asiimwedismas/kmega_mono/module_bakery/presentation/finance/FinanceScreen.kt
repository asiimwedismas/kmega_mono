package me.asiimwedismas.kmega_mono.module_bakery.presentation.finance

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionItem
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddSafeTransactionForm
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AddItemFAB
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AppBar
import me.asiimwedismas.kmega_mono.ui.common_components.DatePickerDialog
import me.asiimwedismas.kmega_mono.ui.common_components.formatAmount
import me.asiimwedismas.kmega_mono.ui.theme.TypographyM2
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    onNavigationIconClick: () -> Unit = {},
    viewModel: FinanceViewModel = hiltViewModel(),
) {

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    val selectedDate by viewModel.dates.selectedDate.observeAsState("")
    val showCalendar by viewModel.showCalendar

    val showAddFab by viewModel.showAddFab
    val showAddItemInput by viewModel.showAddItemInput
    val editStatus by viewModel.editStatus

    val categoryQuery = viewModel.addTransactionFormState.query
    val predictions = viewModel.addTransactionFormState.predictions
    val explanation = viewModel.addTransactionFormState.inputExplanation
    val amount = viewModel.addTransactionFormState.inputAmount
    val submit = viewModel.addTransactionFormState.submit

    val totalTransactions by viewModel.totalTransactions
    val totalCollections by viewModel.totalCollections
    val unaccountedFor by viewModel.unaccountedFor
    val transactionsList by viewModel.transactionsList

    val showCollectionsList by viewModel.showCollectionsList
    val collectionsList by viewModel.collectionsList

    val previousDayFlour by viewModel.previousDayFlour
    val previousDayNetProfit by viewModel.previousDayNetProfit

    val isLoadingData by viewModel.isLoadingData

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = selectedDate,
                onNavigationIconClick = onNavigationIconClick,
                onPreviousDateClick = viewModel::selectPreviousDate,
                onSelectDateClick = viewModel::toggleShowCalendar,
                onNextDateClick = viewModel::selectNextDate,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (showAddFab && !isLoadingData) {
                AddItemFAB(viewModel::onAddFabClick)
            }
        }
    ) {
        if (showCalendar) {
            DatePickerDialog(
                currentSelected = viewModel.dates.instance.value!!.timeInMillis,
                onDateSelected = viewModel::changeDate,
                onDismiss = viewModel::toggleShowCalendar
            )
        }
        Column() {
            if (isLoadingData) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (showAddItemInput) {
                AddSafeTransactionForm(
                    label = "Select category",
                    onQueryChanged = viewModel::onQueryChanged,
                    onExplanationChanged = viewModel::onExplanationChanged,
                    onAmountChanged = viewModel::onAmountChanged,
                    onOptionSelected = viewModel::onCategorySelected,
                    onImeAction = {},
                    onClearClick = viewModel::onClearPredictions,
                    predictionsList = predictions,
                    query = categoryQuery,
                    explanation = explanation,
                    amount = amount,
                    submit = submit,
                    onAddItem = viewModel::onAddTransaction,
                    onExpandPredictions = viewModel::onTogglePredictions
                )
            }
            Column() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleShowCollectionsList() }) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Collections : ${formatAmount(totalCollections)}"
                    )
                    if (showCollectionsList) {
                        CollectionsTable(
                            collections = collectionsList
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                ) {
                    Card(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Used Flour",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = formatAmount(previousDayFlour),
                            style = TypographyM2.h6,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "NP",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = formatAmount(previousDayNetProfit),
                            style = TypographyM2.h6,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Accounting bal",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = formatAmount(unaccountedFor),
                            style = TypographyM2.h6,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TransactionsContent(
                itemsList = transactionsList,
                onSwiped = viewModel::deleteItem,
                editable = editStatus,
                totalTransactions = totalTransactions
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsContent(
    itemsList: List<SafeTransactionItem>,
    onSwiped: (SafeTransactionItem, Int) -> Unit,
    editable: Boolean,
    totalTransactions: Long,
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
    Card {
        Text(
            text = "Transactions : ${formatAmount(totalTransactions)}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn {
            itemsIndexed(
                items = itemsList,
                key = { index, item ->
                    "$index+${item.hashCode()}"
                }
            ) { position, item ->
                TransactionItemHolder(
                    onDeleted = onSwiped,
                    deletable = editable,
                    item = item,
                    position = position,
                    numberFormat = numberFormat
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionItemHolder(
    onDeleted: (SafeTransactionItem, Int) -> Unit,
    deletable: Boolean,
    item: SafeTransactionItem,
    position: Int,
    numberFormat: NumberFormat,
) {
    ListItem(
        text = {
            Text(
                text = "${item.category} : ${numberFormat.format(item.amount)}",
                style = MaterialTheme.typography.titleMedium
            )
        },
        secondaryText = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = item.explanation,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
            }
        },
        trailing = {
            if (deletable) {
                IconButton(
                    onClick = { onDeleted(item, position) }
                ) {
                    Icon(imageVector = Icons.Outlined.Delete,
                        contentDescription = "delete item")
                }
            }
        }
    )
}

@Composable
fun CollectionsTable(
    collections: List<String>,
) {
    for (collection in collections) {
        val portions = collection.split(":")
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = portions[0])
            Text(text = portions[1])
        }
    }
}
