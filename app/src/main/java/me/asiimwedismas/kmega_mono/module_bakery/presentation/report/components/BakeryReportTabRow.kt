/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.module_bakery.presentation.BakeryScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.FactoryScreens
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.BakeryReportScreen
import java.util.*

@Composable
fun BakeryReportTabRow(
    allScreens: List<BakeryReportScreen>,
    onTabSelected: (BakeryReportScreen) -> Unit,
    currentScreen: BakeryReportScreen,
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(Modifier
            .selectableGroup()
            .horizontalScroll(rememberScrollState(), true)) {
            allScreens.forEach { screen ->
                Tab(
                    text = screen.title,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun FactoryScreensTabRow(
    allScreens: List<FactoryScreens>,
    onTabSelected: (FactoryScreens) -> Unit,
    currentScreen: FactoryScreens,
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(Modifier
            .selectableGroup()
            .horizontalScroll(rememberScrollState(), true)) {
            allScreens.forEach { screen ->
                Tab(
                    text = screen.name,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}


@Composable
private fun Tab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    val color = MaterialTheme.colors.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        if (selected) {
            Spacer(Modifier.width(12.dp))
            Text(text.uppercase(Locale.getDefault()), color = tabTintColor)
        }
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

@Composable
fun BakeryScreensTabRow(
    allScreens: List<BakeryScreen>,
    onTabSelected: (BakeryScreen) -> Unit,
    currentScreen: String,
) {
    Surface(
        Modifier
            .fillMaxSize()
    ) {
        Column(Modifier
            .selectableGroup()
            .fillMaxWidth()) {
            allScreens.forEach { screen ->
                ColumnTab(
                    text = screen.name,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = false
                )
            }
        }
    }
}

@Composable
private fun ColumnTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text)
        Spacer(Modifier.width(12.dp))
        Text(text)
    }
}

