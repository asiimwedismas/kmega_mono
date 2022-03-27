package me.asiimwedismas.kmega_mono.module_bakery.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.twotone.AreaChart
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.PieChart
import androidx.compose.material.icons.twotone.PrecisionManufacturing
import androidx.compose.ui.graphics.vector.ImageVector

enum class BakeryScreen(
    val icon: ImageVector,
) {
    Factory(
        icon = Icons.Rounded.Cake
    ),
    Report(
        icon = Icons.Rounded.PieChart
    );

    companion object {
        fun fromRoute(route: String?): String {
            return when (route?.substringBefore("/")) {
                "home", null -> "home"
                Factory.name -> Factory.name
                Report.name -> Report.name
                else -> throw IllegalArgumentException("Route $route doesn't exist")
            }
        }
    }
}
