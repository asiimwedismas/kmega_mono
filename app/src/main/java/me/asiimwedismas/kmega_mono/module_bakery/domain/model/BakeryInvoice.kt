package me.asiimwedismas.kmega_mono.module_bakery.domain.model

enum class InvoiceType {
    DISPATACH,
    OUTLET_DELIVERY,
    AGENT_DELIVERY,
    PRODUCTION,
    RETURNS,
    AUDIT,
    EXPIRED
}

data class BakeryInvoice(
    var document_id: String = "",
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var date: String? = null,
    var utc: Long = 0,
    var isLocked: Boolean = false,
    var salesman_id: String = "",
    var salesman_name: String = "",
    var outlet_id: String? = null,
    var outlet_name: String? = null,
    var agent_id: String? = null,
    var agent_name: String? = null,
    var care_of: String? = null,
    var items_list: List<BakeryInvoiceItem> = ArrayList(),
)

val BakeryInvoice.totalOutletSale
    get() = items_list.sumOf { it.total_outlet_sale }

val BakeryInvoice.totalOutletProfit
    get() = items_list.sumOf { it.outlet_profit }

val BakeryInvoice.totalAgentSale
    get() = items_list.sumOf { it.total_agent_sale }

val BakeryInvoice.totalAgentProfit
    get() = items_list.sumOf { it.agent_profit }

val BakeryInvoice.totalFactorySale
    get() = items_list.sumOf { it.total_factory_sale }

val BakeryInvoice.totalFactoryProfit
    get() = items_list.sumOf { it.factory_profit }