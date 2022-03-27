package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.store_balance

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoiceItem
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddProductForm
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.InvoiceProductHolder
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AddItemFAB
import me.asiimwedismas.kmega_mono.ui.common_components.DatePickerDialog
import me.asiimwedismas.kmega_mono.ui.common_components.formatAmount
import me.asiimwedismas.kmega_mono.ui.theme.TypographyM2
import java.text.NumberFormat
import java.util.*


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Preview
@Composable
fun StoreBalanceScreen(
    viewModel: StoreBalanceViewModel = hiltViewModel(),
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.pinnedScrollBehavior { false }
    }

    val selectedDate by viewModel.dates.selectedDate.observeAsState("")
    val showCalendar by viewModel.showCalendar
    val itemsList by viewModel.itemsList
    val totalProductionAmount by viewModel.totalFactoryProduction
    val totalGrossProfit by viewModel.totalGrossProfit
    val totalNetProfit by viewModel.totalNetProfit

    val productQuery = viewModel.addProductFormState.query
    val predictionsList = viewModel.addProductFormState.predictions
    val products by viewModel.productList.observeAsState()
    LaunchedEffect(key1 = products) {
        if (products != null) {
            viewModel.addProductFormState.optionsList = products as List<BakeryProduct>
        }
    }

    val productQty = viewModel.addProductFormState.qtyInput
    val submit = viewModel.addProductFormState.submit
    val showAddFab by viewModel.showAddFab
    val showAddItemInput by viewModel.showAddItemInput
    val editStatus by viewModel.editStatus

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        floatingActionButton = {
            if (showAddFab) {
                AddItemFAB(
                    viewModel::onAddFabClick
                )
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

        Column {
            if (showAddItemInput) {
                AddProductForm(
                    label = "Select product",
                    query = productQuery,
                    onQueryChanged = viewModel::onQueryChanged,
                    predictionsList = predictionsList,
                    onOptionSelected = viewModel.addProductFormState::onProductSelected,
                    onImeAction = viewModel.addProductFormState::clearPredictions,
                    onClearClick = viewModel.addProductFormState::clearAutoCompleteField,
                    productQty = productQty,
                    onQtyChanged = viewModel.addProductFormState::onChangeQty,
                    submit = submit,
                    onAddItem = viewModel::onAddItem
                )
            }
            Box(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "Factory audited stock",
                        style = TypographyM2.body1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = formatAmount(totalProductionAmount),
                        style = TypographyM2.h2,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Row {
                Box(Modifier.weight(0.5f)) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = "GP",
                            style = TypographyM2.body1,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = formatAmount(totalGrossProfit),
                            style = TypographyM2.h4,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Box(Modifier.weight(0.5f)) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = "NP",
                            style = TypographyM2.body1,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = formatAmount(totalNetProfit),
                            style = TypographyM2.h4,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            InvoiceContent(
                itemsList = itemsList,
                onSwiped = viewModel::deleteItem,
                editable = editStatus
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InvoiceContent(
    itemsList: List<BakeryInvoiceItem>,
    onSwiped: (BakeryInvoiceItem, Int) -> Unit,
    editable: Boolean = true,
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
    LazyColumn {
        itemsIndexed(
            items = itemsList,
            key = { index, item ->
                "$index+${item.hashCode()}"
            }
        ) { position, item ->
            InvoiceProductHolder(
                onDeleted = onSwiped,
                deletable = editable,
                item = item,
                product = item.product_name,
                qty = item.qty,
                position = position,
                amount = item.total_factory_sale,
                numberFormat = numberFormat
            )
        }
    }
}


