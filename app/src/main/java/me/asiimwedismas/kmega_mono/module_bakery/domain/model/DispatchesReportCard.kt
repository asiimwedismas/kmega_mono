package me.asiimwedismas.kmega_mono.module_bakery.domain.model

class DispatchesReportCard(
    var dispatches: Long = 0,
    var returns: Long = 0,
    private val outletList: List<BakeryInvoice>,
    private val agentList: List<BakeryInvoice>,
    private val expiredList: List<BakeryInvoice>,
) {
    var outlets: Long = 0
        private set
    var agentDiscounts: Long = 0
        private set
    var expired: Long = 0
        private set
    var shortage: Long = 0
        private set

    fun calulate() {
        outlets = outletList.sumOf { it.totalFactorySale }.toLong()
        expired = expiredList.sumOf { it.totalFactorySale }.toLong()
        agentDiscounts =
            agentList.sumOf { it.totalFactorySale } - agentList.sumOf { it.totalAgentSale }.toLong()

    }

    companion object {
        fun createEmpty(): DispatchesReportCard {
            return DispatchesReportCard(
                0,
                0,
                emptyList(),
                emptyList(),
                emptyList()
            )
        }
    }
}