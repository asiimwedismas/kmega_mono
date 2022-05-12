package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class FactoryReportCard(
    val preProducedList: List<FactoryProductionSheet>,
    val preAuditedList: List<BakeryInvoice>,
    val preReturnedList: List<BakeryInvoice>,
    val producedList: List<FactoryProductionSheet>,
    val dispatchedList: List<BakeryInvoice>,
    val returnedList: List<BakeryInvoice>,
    val expiredList: List<BakeryInvoice>,
    val auditedList: List<BakeryInvoice>,
    val preIngredients: List<UsedIngredientsSheet>,
) {
    var preProducted: Long = 0
        private set
    var preAudited: Long = 0
        private set
    var preReturned: Long = 0
        private set
    var produced: Long = 0
        private set
    var dispatched: Long = 0
        private set
    var returned: Long = 0
        private set
    var expired: Long = 0
        private set
    var audited: Long = 0
        private set
    var unaccountedFor: Long = 0
        private set
    var openingStock: Long = 0
        private set
    var closingStock: Long = 0
        private set

    var dispatchedBreakDown: DispatchedBreakDown
    private set

    init {
        preProducted = preProducedList.sumOf { it.totalWholeSales }.toLong()
        produced = producedList.sumOf { it.totalWholeSales }.toLong()

        dispatched = dispatchedList.sumOf { it.totalFactorySale }.toLong()
        returned = returnedList.sumOf { it.totalFactorySale }.toLong()
        preReturned = preReturnedList.sumOf { it.totalFactorySale }.toLong()
        expired = expiredList.sumOf { it.totalFactorySale }.toLong()
        preAudited = preAuditedList.sumOf { it.totalFactorySale }.toLong()
        audited = auditedList.sumOf { it.totalFactorySale }.toLong()

        openingStock = preAudited + preProducted + preReturned
        closingStock = audited + produced + returned

        val accountedFor = audited + dispatched + expired
        unaccountedFor = accountedFor - openingStock

        dispatchedBreakDown = DispatchedBreakDown(this)
    }

    companion object {
        fun emptyCard(): FactoryReportCard {
            return FactoryReportCard(
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
            )
        }
    }

}