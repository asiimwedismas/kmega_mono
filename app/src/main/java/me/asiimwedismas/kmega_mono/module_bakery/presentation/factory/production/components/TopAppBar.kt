package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import me.asiimwedismas.kmega_mono.ui.theme.TypographyM2
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
        navigationIcon = {
            IconButton(onClick = {
                onNavigationIconClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "home"
                )
            }
        },
        title = {
            Text(
                text = title
            )
        },
        actions = {
            IconButton(onClick = {
                onPreviousDateClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = "Previous date"
                )
            }
            IconButton(onClick = {
                onSelectDateClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = "Pick date"
                )
            }
            IconButton(onClick = {
                onNextDateClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = "Next date"
                )
            }
        }
    )
}

@Composable
fun MeduimAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onNavigationIconClick: () -> Unit,
    onPreviousDateClick: () -> Unit,
    onSelectDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = {
                onNavigationIconClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "home"
                )
            }
        },
        title = title,
        actions = {
            IconButton(onClick = {
                onPreviousDateClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = "Previous date"
                )
            }
            IconButton(onClick = {
                onSelectDateClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = "Pick date"
                )
            }
            IconButton(onClick = {
                onNextDateClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = "Next date"
                )
            }
        }
    )
}