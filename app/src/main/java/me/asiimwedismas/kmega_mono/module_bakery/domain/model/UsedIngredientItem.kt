package me.asiimwedismas.kmega_mono.module_bakery.domain.model

class UsedIngredientItem(
    var name: String = "",
    var measurement_unit: String = "",
    var used_qty: Float = 0F,
    var total_cost: Int = 0,
) {
    constructor(
        ingredient: BakeryIngredient,
        qty: Float,
    ) : this(
        name = ingredient.ingredient_name,
        used_qty = qty,
        measurement_unit = ingredient.ingredient_pack_unit,
        total_cost = (ingredient.ingredient_unit_price * qty).toInt()
    )
}
