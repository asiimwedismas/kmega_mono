package me.asiimwedismas.bakery_module.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bakery_products")
data class BakeryProduct(
    @PrimaryKey(autoGenerate = false) val product: String,
    var pack_quantity: Int = 0,
    var kavera: Double = 0.0,
    var out_per_bag: Double = 0.0,
    var wholesale_price: Double = 0.0,
    var retail_price: Double = 0.0,
    var ingredients_cost_per_bag: Double = 0.0,
    var fixed_cop: Double = 0.0,
    var agent_price: Double = 0.0
) {
    override fun toString(): String {
        return "$product @ $wholesale_price - $retail_price"
    }
}