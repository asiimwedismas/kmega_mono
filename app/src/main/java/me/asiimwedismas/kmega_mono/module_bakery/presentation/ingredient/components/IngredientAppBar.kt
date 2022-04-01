package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun IngredientAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navAction: () -> Unit,
    onRefresh: () -> Unit,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text("Ingredients") },
        navigationIcon = {
            IconButton(onClick = navAction) {
                Icon(imageVector = Icons.Outlined.NavigateBefore, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { onRefresh() }) {
                Icon(
                    imageVector = Icons.Rounded.Sync,
                    contentDescription = "Sync"
                )
            }
        }
    )
}