package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun AddItemFAB(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add item")
    }
}