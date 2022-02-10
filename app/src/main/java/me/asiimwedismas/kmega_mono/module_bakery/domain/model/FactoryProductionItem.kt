package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class FactoryProductionItem(
    var product_name: String = "",
    var produced_qty: Int = 0,
    var buvera: Int = 0,
    var ingredient_costs: Int = 0,
    var fixed_costs: Int = 0,
    var wholesale_sales: Int = 0,
    var profit_no_fixed_cop: Int = 0
) {
    fun addObject(otherItem: FactoryProductionItem) {
        product_name = otherItem.product_name
        produced_qty += otherItem.produced_qty
        buvera += otherItem.buvera
        ingredient_costs += otherItem.ingredient_costs
        fixed_costs += otherItem.fixed_costs
        wholesale_sales += otherItem.wholesale_sales
        profit_no_fixed_cop += otherItem.profit_no_fixed_cop
    }
}