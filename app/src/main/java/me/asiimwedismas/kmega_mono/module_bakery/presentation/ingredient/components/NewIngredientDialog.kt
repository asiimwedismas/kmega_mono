package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewIngredientDialog(
    
) {
    AlertDialog(
        title = { Text("New Ingredient") },
        onDismissRequest = { },
        confirmButton = {},

    )
}

@Preview()
@Composable
fun NewIngredientForm() {
    Column {
    }
}