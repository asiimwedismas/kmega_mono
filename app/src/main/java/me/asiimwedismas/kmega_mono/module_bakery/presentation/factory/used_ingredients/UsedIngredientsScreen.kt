package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.used_ingredients

import androidx.compose.animation.rememberSplineBasedDecay
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.UsedIngredientItem
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddProductForm
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AddItemFAB
import me.asiimwedismas.kmega_mono.ui.common_components.formatAmount
import me.asiimwedismas.kmega_mono.ui.theme.TypographyM2
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun UsedIngredientsScreen(
    viewModel: UsedIngredientsViewModel = hiltViewModel(),
) {
    val itemsList by viewModel.itemsList
    val totalIngredientsCost by viewModel.totalIngredientsCost

    val ingredientQuery = viewModel.addIngredientFormState.query
    val predictionsList = viewModel.addIngredientFormState.predictions
    val ingredients by viewModel.ingredientsList.observeAsState()
    LaunchedEffect(key1 = ingredients) {
        if (ingredients != null) {
            viewModel.addIngredientFormState.optionsList = ingredients as List<BakeryIngredient>
        }
    }

    val ingredientQty = viewModel.addIngredientFormState.qtyInput
    val submit = viewModel.addIngredientFormState.submit
    val qtyLabel = viewModel.addIngredientFormState.qtyInputHint
    val showAddFab by viewModel.showAddFab
    val showAddItemInput by viewModel.showAddItemInput
    val editStatus by viewModel.editStatus

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (showAddFab) {
                AddItemFAB(
                    viewModel::onAddFabClick
                )
            }
        }
    ) {
        Column {
            if (showAddItemInput) {
                AddProductForm(
                    label = "Select Ingredient",
                    qtyLabel = qtyLabel,
                    query = ingredientQuery,
                    onQueryChanged = viewModel::onQueryChanged,
                    predictionsList = predictionsList,
                    onOptionSelected = viewModel::onIngredientSelected,
                    onImeAction = viewModel.addIngredientFormState::clearPredictions,
                    onClearClick = viewModel.addIngredientFormState::clearAutoCompleteField,
                    productQty = ingredientQty,
                    onQtyChanged = viewModel.addIngredientFormState::onChangeQty,
                    submit = submit,
                    onAddItem = viewModel::onAddItem
                )
            }
            Box(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "Total used ingredients",
                        style = TypographyM2.body1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = formatAmount(totalIngredientsCost),
                        style = TypographyM2.h2,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            UsedIngredientsContent(
                itemsList = itemsList,
                onSwiped = viewModel::deleteItem,
                editable = editStatus
            )
        }
    }
}

@Composable
fun UsedIngredientsContent(
    itemsList: List<UsedIngredientItem>,
    onSwiped: (UsedIngredientItem, Int) -> Unit,
    editable: Boolean,
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
    LazyColumn {
        itemsIndexed(
            items = itemsList,
            key = { index, item ->
                "$index+${item.hashCode()}"
            }
        ) { position, item ->
            UsedIngredientItemHolder(
                onDeleted = onSwiped,
                deletable = editable,
                item = item,
                position = position,
                numberFormat = numberFormat
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsedIngredientItemHolder(
    onDeleted: (UsedIngredientItem, Int) -> Unit,
    deletable: Boolean,
    item: UsedIngredientItem,
    position: Int,
    numberFormat: NumberFormat,
) {
    ListItem(
        text = {
            Text(
                text = "${item.name} (${numberFormat.format(item.used_qty)} ${item.measurement_unit})",
                style = MaterialTheme.typography.titleMedium
            )
        },
        secondaryText = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "Ugx ${numberFormat.format(item.total_cost)}",
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

