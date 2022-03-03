package me.asiimwedismas.kmega_mono.module_bakery.domain.model

class OutletReportCard(
    var outletsDaySheet: OutletsDaySheet,
) {
    var openingStock: Long = 0
        private set
    var handovers: Long = 0
        private set
    var expenditure: Long = 0
        private set
    var closingStock: Long = 0
        private set
    var auditedShortage: Long = 0
        private set
    var expired: Long = 0
        private set
    var unaccountedFor: Long = 0
        private set

    init {
        openingStock = outletsDaySheet.outletStandingList.sumOf { it.brought_forward + it.received }
        handovers = outletsDaySheet.outletStandingList.sumOf { it.cash_handover }
        expenditure = outletsDaySheet.outletStandingList.sumOf { it.expenditure }
        expired = outletsDaySheet.outletStandingList.sumOf { it.damages }
        closingStock = outletsDaySheet.outletStandingList.sumOf { it.actual_carry_forward }
        auditedShortage = outletsDaySheet.outletStandingList.sumOf {
            if (it.audit_shortage_excess < 0) it.audit_shortage_excess else 0
        }

        calculatedMissingMoneys()
    }

    fun calculate(deliveries: Long) {
        openingStock = outletsDaySheet.outletStandingList.sumOf { it.brought_forward } + deliveries
        calculatedMissingMoneys()
    }

    private fun calculatedMissingMoneys() {
        val accountedFor = handovers + expenditure + expired + auditedShortage
        unaccountedFor = (openingStock - accountedFor) - closingStock
    }

    companion object {
        fun createEmpty(): OutletReportCard {
            return OutletReportCard(
                OutletsDaySheet(),
            )
        }
    }
}