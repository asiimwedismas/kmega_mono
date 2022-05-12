package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AddIngredientFAB(
    action: () -> Unit,
) {
    ExtendedFloatingActionButton(
        text = { Text("New Ingredient") },
        onClick = { action() },
        icon = {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
        }
    )
}