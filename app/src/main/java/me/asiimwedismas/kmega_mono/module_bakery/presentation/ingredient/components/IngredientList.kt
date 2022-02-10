package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.IngredientViewModel
import java.text.NumberFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun IngredientList(
    list: List<BakeryIngredient>,
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        itemsIndexed(
            items = list
        ) { _, item ->
            IngredientItemHolder(
                ingredient = item,
                numberFormat = numberFormat
            )
        }
    }
}