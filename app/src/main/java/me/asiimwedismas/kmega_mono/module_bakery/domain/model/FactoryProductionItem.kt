package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class FactoryProductionItem(
    var product_name: String = "",
    var produced_qty: Int = 0,
    var buvera: Int = 0,
    var ingredient_costs: Int = 0,
    var fixed_costs: Int = 0,
    var wholesale_sales: Int = 0,
    var profit_gross: Int = 0,
    var profit_net: Int = 0,
) {

    constructor(product: BakeryProduct, qty: Int) : this(product_name = product.product,
        produced_qty = qty) {
        calculateCostsAndProfits(product, qty)
    }

    private fun calculateCostsAndProfits(product: BakeryProduct, qty: Int) {
        buvera = product.kavera * qty
        ingredient_costs = product.ingredients_cost_per_bag / product.out_per_bag * qty
        fixed_costs = product.fixed_cop / product.out_per_bag * qty
        wholesale_sales = product.wholesale_price * qty

        val bagSales = product.out_per_bag * product.wholesale_price
        val bagPackage = product.kavera * product.out_per_bag
        val bagGrossProfit = bagSales - (product.ingredients_cost_per_bag + bagPackage)
        val bagNetProfit = bagGrossProfit - product.fixed_cop

        profit_gross = bagGrossProfit / product.out_per_bag * qty
        profit_net = bagNetProfit / product.out_per_bag * qty
    }
}