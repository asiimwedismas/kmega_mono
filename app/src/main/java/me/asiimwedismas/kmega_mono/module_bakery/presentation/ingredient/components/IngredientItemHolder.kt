package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.ui.theme.Kmega_monoTheme
import java.text.NumberFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun IngredientItemHolder(
    ingredient: BakeryIngredient,
    numberFormat: NumberFormat,
) {
    ListItem(
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = ingredient.ingredient_name
                )
                Text(
                    text = "${ingredient.ingredient_unit_price} / ${ingredient.ingredient_pack_unit}"
                )
            }
        },
        secondaryText = {
            Text(
                text = "${numberFormat.format(ingredient.ingredient_pack_qty)} ${ingredient.ingredient_pack_unit} @ ${
                    numberFormat.format(ingredient.ingredient_price)
                }")
        }
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewIngredientHolder() {

    val ingredient = BakeryIngredient(
        "Sugar",
        145000,
        50,
        "kg",
        2300
    )

    androidx.compose.material3.Surface(color = MaterialTheme.colorScheme.background) {
        IngredientItemHolder(ingredient, NumberFormat.getNumberInstance(Locale.UK))
    }

}