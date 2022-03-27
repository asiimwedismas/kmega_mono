package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.twotone.Calculate
import androidx.compose.material.icons.twotone.PrecisionManufacturing
import androidx.compose.material.icons.twotone.Recycling
import androidx.compose.ui.graphics.vector.ImageVector
import java.lang.IllegalArgumentException

enum class FactoryScreens(
    val icon: ImageVector,
){
    Production(
        icon = Icons.Rounded.Cake
    ),
    Damages(
        icon = Icons.TwoTone.Recycling
    ),
    Audit(
        icon = Icons.TwoTone.Calculate
    );

    companion object{
        fun fromRoute(route: String?): FactoryScreens {
            return when(route?.substringBefore("/")){
                Production.name, null -> Production
                Damages.name -> Damages
                Audit.name -> Audit
                else -> throw IllegalArgumentException("Route $route doesn't exist")
            }
        }
    }
}