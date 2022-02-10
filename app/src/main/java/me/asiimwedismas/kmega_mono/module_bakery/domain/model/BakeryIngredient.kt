package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bakery_ingredients")
data class BakeryIngredient(
    @PrimaryKey(autoGenerate = false) var ingredient_name: String = "",
    var ingredient_price: Int = 0,
    var ingredient_pack_qty: Int = 0,
    var ingredient_pack_unit: String = "",
    var ingredient_unit_price: Int = 0
) {
    override fun toString() = ingredient_name
}