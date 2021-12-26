package me.asiimwedismas.bakery_module.domain.model

data class FactoryProductionItem(
    var product_name: String,
    var produced_qty: Int,
    var buvera: Double,
    var ingredient_costs: Double,
    var fixed_costs: Double,
    var wholesale_sales: Double,
    var profit_no_fixed_cop: Double,
    var expired: Double
) {
    fun addObject(otherItem: FactoryProductionItem) {
        product_name = otherItem.product_name
        produced_qty += otherItem.produced_qty
        buvera += otherItem.buvera
        ingredient_costs += otherItem.ingredient_costs
        fixed_costs += otherItem.fixed_costs
        wholesale_sales += otherItem.wholesale_sales
        profit_no_fixed_cop += otherItem.profit_no_fixed_cop
        expired += otherItem.expired
    }
}