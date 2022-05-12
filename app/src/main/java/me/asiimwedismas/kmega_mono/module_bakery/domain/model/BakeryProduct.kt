package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bakery_products")
data class BakeryProduct(
    @PrimaryKey(autoGenerate = false) val product: String = "",
    var pack_quantity: Int = 0,
    var kavera:  Int = 0,
    var out_per_bag:  Int = 0,
    var wholesale_price:  Int = 0,
    var retail_price:  Int = 0,
    var ingredients_cost_per_bag:  Int = 0,
    var fixed_cop:  Int = 0,
    var agent_price:  Int = 0,
) {
    override fun toString(): String {
        return "$product @ $wholesale_price - $retail_price"
    }
}