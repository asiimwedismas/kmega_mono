package me.asiimwedismas.kmega_mono.module_bakery.presentation.report

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class BakeryReportScreen(
    val icon: ImageVector,
    val title: String
) {
    Analysis(
        icon = Icons.TwoTone.Analytics,
        title = "Profit analysis"
    ),
    Factory(
        icon = Icons.TwoTone.Business,
        title = "Store"
    ),
    Dispatched(
        icon = Icons.TwoTone.LocalShipping,
        title = "Dispatched"
    ),
    Outlets(
        icon = Icons.TwoTone.Store,
        title = "Outlets"
    ),
    Moneys(
        icon = Icons.TwoTone.MonetizationOn,
        title = "Collection spending"
    );

    companion object {
        fun fromRoute(route: String?): BakeryReportScreen {
            return when (route?.substringBefore("/")) {
                Factory.name, null -> Factory
                Dispatched.name -> Dispatched
                Outlets.name -> Outlets
                Analysis.name -> Analysis
                Moneys.name -> Moneys
                else -> throw IllegalArgumentException("Route $route doesn't exist")
            }
        }
    }
}