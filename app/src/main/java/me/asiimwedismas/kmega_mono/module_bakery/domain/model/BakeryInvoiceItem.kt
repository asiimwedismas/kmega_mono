package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class BakeryInvoiceItem(
    var product_name: String = "",
    var qty: Int = 0,
    var factory_profit_gross: Int = 0,
    var outlet_profit_gross: Int = 0,
    var agent_profit_gross: Int = 0,
    var factory_profit_net: Int = 0,
    var outlet_profit_net: Int = 0,
    var agent_profit_net: Int = 0,
    var total_agent_sale: Int = 0,
    var total_factory_sale: Int = 0,
    var total_outlet_sale: Int = 0,
) {
    constructor(product: BakeryProduct, qty: Int) : this(product_name = product.product,
        qty = qty) {
        calculateSalesAndProfits(product)
    }

    private fun calculateSalesAndProfits(product: BakeryProduct) {
        val wholesalePrice = product.wholesale_price
        val retailPrice = product.retail_price
        val agentPrice = product.agent_price
        val outPerBag = product.out_per_bag
        val packagePerBag = product.kavera * outPerBag
        val bagProductionCost = product.ingredients_cost_per_bag + packagePerBag

        val factoryBagProfitGross = (outPerBag * wholesalePrice - bagProductionCost)
        val outletBagProfitGross = (outPerBag * retailPrice - bagProductionCost)
        val agentBagProfitGross = (outPerBag * agentPrice - bagProductionCost)

        total_factory_sale = wholesalePrice * qty
        total_outlet_sale = retailPrice * qty
        total_agent_sale = agentPrice * qty

        factory_profit_gross = factoryBagProfitGross / outPerBag * qty
        outlet_profit_gross = outletBagProfitGross / outPerBag * qty
        agent_profit_gross = agentBagProfitGross / outPerBag * qty

        factory_profit_net = (factoryBagProfitGross - product.fixed_cop) / outPerBag * qty
        outlet_profit_net = (outletBagProfitGross - product.fixed_cop) / outPerBag * qty
        agent_profit_net = (agentBagProfitGross - product.fixed_cop) / outPerBag * qty
    }
}

