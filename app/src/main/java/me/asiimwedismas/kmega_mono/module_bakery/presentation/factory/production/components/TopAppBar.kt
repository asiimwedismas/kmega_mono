package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.*

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit,
    onPreviousDateClick: () -> Unit,
    onSelectDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    SmallTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(title) },
        actions = {
            IconButton(onClick = {
                onPreviousDateClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.SkipPrevious,
                    contentDescription = "Previous date"
                )
            }
            IconButton(onClick = {
                onSelectDateClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Pick date"
                )
            }
            IconButton(onClick = {
                onNextDateClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.SkipNext,
                    contentDescription = "Next date"
                )
            }
        }
    )
}