package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bakery_product_ingredients", primaryKeys = ["product_name", "ingredient"])
data class BakeryProductIngredient(
    var product_name: String,
    var ingredient: String,
    var quantity: Double,
    var amount: Double,
)