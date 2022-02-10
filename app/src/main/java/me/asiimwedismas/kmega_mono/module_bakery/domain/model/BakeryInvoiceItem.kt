package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class BakeryInvoiceItem(
    var product_name: String = "",
    var qty: Int = 0,
    var factory_profit: Int = 0,
    var outlet_profit:  Int = 0,
    var agent_profit:  Int = 0,
    var total_agent_sale:  Int = 0,
    var total_factory_sale:  Int = 0,
    var total_outlet_sale:  Int = 0
)

fun BakeryInvoiceItem.calculateSalesAndProfits(product: BakeryProduct) {

    val ingredientsCostPerBag = product.ingredients_cost_per_bag
    val kavera = product.kavera
    val wholesalePrice = product.wholesale_price
    val retailPrice = product.retail_price
    val agentPrice = product.agent_price
    val outPerBag = product.out_per_bag
    val packagePerBag = kavera * outPerBag

    val factoryBagProfitGross =
        (outPerBag * wholesalePrice - ingredientsCostPerBag - packagePerBag)

    val outletBagProfitGross =
        (outPerBag * retailPrice - ingredientsCostPerBag - packagePerBag)

    val agentBagProfitGross =
        (outPerBag * agentPrice - ingredientsCostPerBag - packagePerBag)

    total_factory_sale = wholesalePrice * qty
    total_outlet_sale = retailPrice * qty
    total_agent_sale = agentPrice * qty

    factory_profit = factoryBagProfitGross / outPerBag * qty
    outlet_profit = outletBagProfitGross / outPerBag * qty
    agent_profit = agentBagProfitGross / outPerBag * qty
}