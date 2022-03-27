package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import kotlin.math.abs

class DispatchesReportCard(
    var dispatches: Long = 0,
    var returns: Long = 0,
    var outletList: List<BakeryInvoice>,
    var agentList: List<BakeryInvoice>,
    var expiredList: List<BakeryInvoice>,
    var handoversList: List<CashierStanding>,
    var expendituresList: List<FieldExpenditure>,
) {
    var outletsDeliveries: Long = 0
        private set
    var handovers: Long = 0
        private set
    var debitSales: Long = 0
        private set
    var fieldExpenditures: Long = 0
        private set
    var agentDiscounts: Long = 0
        private set
    var fieldReplacements: Long = 0
        private set
    var salesmenShortages: Long = 0
        private set
    var unaccounted: Long = 0
        private set
    private var debitsPaid: Long = 0

    fun calulate() {
        outletsDeliveries = outletList.sumOf {
            it.totalFactorySale
        }.toLong()

        fieldReplacements = expiredList.sumOf {
            it.totalFactorySale
        }.toLong()

        fieldExpenditures = expendituresList.sumOf { entry ->
            entry.expenditures.sumOf { it.amount }
        }.toLong()

        agentDiscounts = agentList.sumOf { it.totalFactorySale } -
                agentList.sumOf { it.totalAgentSale }.toLong()

        handoversList.forEach {
            handovers += it.handover_ed
            debitSales += it.debit_sales_ed
            debitsPaid += it.debits_paid_ed
            salesmenShortages += if (it.variance_ed < 0) it.variance_ed else 0
        }

        val accountedFor = handovers +
                outletsDeliveries +
                returns +
                fieldReplacements +
                debitSales +
                fieldExpenditures +
                agentDiscounts +
                abs(salesmenShortages) -
                debitsPaid

        unaccounted = accountedFor - dispatches

    }

    companion object {
        fun createEmpty(): DispatchesReportCard {
            return DispatchesReportCard(
                0,
                0,
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
            )
        }
    }
}