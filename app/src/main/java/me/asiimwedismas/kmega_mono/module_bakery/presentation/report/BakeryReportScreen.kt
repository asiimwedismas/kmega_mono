package me.asiimwedismas.kmega_mono.module_bakery.presentation.report

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class BakeryReportScreen(
    val icon: ImageVector,
) {
    Breakdown(
        icon = Icons.TwoTone.Functions
    ),
    Factory(
        icon = Icons.TwoTone.PrecisionManufacturing
    ),
    Dispatches(
        icon = Icons.TwoTone.Outbound
    ),
    Outlets(
        icon = Icons.TwoTone.Store
    );

    companion object {
        fun fromRoute(route: String?): BakeryReportScreen {
            return when (route?.substringBefore("/")) {
                Factory.name, null -> Factory
                Dispatches.name -> Dispatches
                Outlets.name -> Outlets
                Breakdown.name -> Breakdown
                else -> throw IllegalArgumentException("Route $route doesn't exist")
            }
        }
    }
}