package me.asiimwedismas.kmega_mono.module_bakery.domain.model

enum class InvoiceType {
    DISPATCH,
    OUTLET_DELIVERY,
    AGENT_DELIVERY,
    RETURNS,
    AUDIT,
    EXPIRED;

    companion object{
        @Throws(IllegalArgumentException::class)
        fun fromString(type: String): InvoiceType {
            return when (type) {
                DISPATCH.name -> DISPATCH
                OUTLET_DELIVERY.name -> OUTLET_DELIVERY
                AGENT_DELIVERY.name -> AGENT_DELIVERY
                RETURNS.name -> RETURNS
                AUDIT.name -> AUDIT
                EXPIRED.name -> EXPIRED

                else -> {
                    throw IllegalArgumentException("Wrong category")
                }
            }
        }
    }
}

data class BakeryInvoice(
    var document_id: String = "",
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var date: String? = null,
    var utc: Long = 0,
    var isLocked: Boolean = false,
    var type: String? = null,
    var salesman_id: String = "",
    var salesman_name: String = "",
    var outlet_id: String? = null,
    var outlet_name: String? = null,
    var agent_id: String? = null,
    var agent_name: String? = null,
    var care_of: String? = null,
    var items: MutableList<BakeryInvoiceItem> = ArrayList(),
)

val BakeryInvoice.totalOutletSale
    get() = items.sumOf { it.total_outlet_sale }

val BakeryInvoice.totalOutletProfitGross
    get() = items.sumOf { it.outlet_profit_gross }

val BakeryInvoice.totalAgentSale
    get() = items.sumOf { it.total_agent_sale }

val BakeryInvoice.totalAgentProfitGross
    get() = items.sumOf { it.agent_profit_gross }

val BakeryInvoice.totalFactorySale
    get() = items.sumOf { it.total_factory_sale }

val BakeryInvoice.totalFactoryProfitGross
    get() = items.sumOf { it.factory_profit_gross }

val BakeryInvoice.totalFactoryProfitNet
    get() = items.sumOf { it.factory_profit_net }

val BakeryInvoice.totalAgentProfitNet
    get() = items.sumOf { it.agent_profit_net }

val BakeryInvoice.totalOutletProfitNet
    get() = items.sumOf { it.outlet_profit_net }