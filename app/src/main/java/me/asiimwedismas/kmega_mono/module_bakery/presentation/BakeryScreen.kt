package me.asiimwedismas.kmega_mono.module_bakery.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class BakeryScreen(
    val icon: ImageVector,
    val title: String
) {
    Ingredients(
        icon = Icons.TwoTone.ShoppingCart,
        title = "Ingredients"
    ),
    Factory(
        icon = Icons.Rounded.Cake,
        title = "Factory activities"
    ),
    Report(
        icon = Icons.Rounded.PieChart,
        title = "Date stock report"
    ),
    Expenditures(
        icon = Icons.Rounded.AccountBalanceWallet,
        title = "Safe transactions"
    ),
    SalesmenDeficitRange(
        icon = Icons.Rounded.DateRange,
        title = "Salesmen period deficits"
    );

    companion object {
        fun fromRoute(route: String?): String {
            return when (route?.substringBefore("/")) {
                "home", null -> "home"
                Factory.name -> Factory.name
                Report.name -> Report.name
                Ingredients.name -> Ingredients.name
                Expenditures.name -> Expenditures.name
                SalesmenDeficitRange.name -> SalesmenDeficitRange.name
                else -> throw IllegalArgumentException("Route $route doesn't exist")
            }
        }
    }
}
