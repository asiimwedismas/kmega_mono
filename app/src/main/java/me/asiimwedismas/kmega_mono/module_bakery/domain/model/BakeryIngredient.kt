package me.asiimwedismas.bakery_module.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bakery_ingredients")
data class BakeryIngredient(
    @PrimaryKey(autoGenerate = false) var ingredient_name: String,
    var ingredient_price: Double = 0.0,
    var ingredient_pack_qty: Double = 0.0,
    var ingredient_pack_unit: String,
    var ingredient_unit_price: Double = 0.0
) {
    override fun toString() = ingredient_name
}