package me.asiimwedismas.bakery_module.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bakery_product_ingredients")
data class BakeryProductIngredient(
    @PrimaryKey var id: Int,
    var product_name: String,
    var ingredient: String,
    var quantity: Double,
    var amount: Double,
)