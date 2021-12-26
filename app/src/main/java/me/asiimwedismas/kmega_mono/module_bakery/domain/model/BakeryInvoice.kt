package me.asiimwedismas.bakery_module.domain.model

data class BakeryInvoice(
    var document_id: String? = null,
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var date: String? = null,
    var utc: Long = 0,
    var isLocked: Boolean = false,
    var salesman_id: String? = null,
    var salesman_name: String? = null,
    var outlet_id: String? = null,
    var outlet_name: String? = null,
    var agent_id: String? = null,
    var agent_name: String? = null,
    var items_list: List<BakeryInvoiceItem> = ArrayList()
) {
    fun calculateTotalFactoryProfit() = items_list.sumOf { it.factory_profit }

    fun calculateTotalOutletProfit() = items_list.sumOf { it.outlet_profit }

    fun calculateTotalAgentProfit() = items_list.sumOf { it.agent_profit }

    fun calculateTotalTotalAgentSale() = items_list.sumOf { it.total_agent_sale }

    fun calculateTotalFactorySale() = items_list.sumOf { it.total_factory_sale }

    fun calculateTotalOutletSale() = items_list.sumOf { it.total_outlet_sale }

}