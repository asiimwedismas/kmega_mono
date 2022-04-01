package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.twotone.Calculate
import androidx.compose.material.icons.twotone.FoodBank
import androidx.compose.material.icons.twotone.PrecisionManufacturing
import androidx.compose.material.icons.twotone.Recycling
import androidx.compose.ui.graphics.vector.ImageVector
import java.lang.IllegalArgumentException

enum class FactoryScreens(
    val icon: ImageVector,
    val title: String
){
    Production(
        icon = Icons.Rounded.Cake,
        title = "Production"
    ),
    Damages(
        icon = Icons.TwoTone.Recycling,
        title = "Store damages"
    ),
    Audit(
        icon = Icons.TwoTone.Calculate,
        title = "Closing Stock"
    ),
    Used_Ingredients(
        icon = Icons.TwoTone.FoodBank,
        title = "Ingredients"
    );

    companion object{
        fun fromRoute(route: String?): FactoryScreens {
            return when(route?.substringBefore("/")){
                Production.name, null -> Production
                Damages.name -> Damages
                Audit.name -> Audit
                Used_Ingredients.name -> Used_Ingredients
                else -> throw IllegalArgumentException("Route $route doesn't exist")
            }
        }
    }
}