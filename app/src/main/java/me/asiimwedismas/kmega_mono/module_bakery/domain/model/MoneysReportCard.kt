package me.asiimwedismas.kmega_mono.module_bakery.domain.model

class MoneysReportCard(
    dispatchesReportCard: DispatchesReportCard,
    outletReportCard: OutletReportCard,
) {
    var totalCollections: Long = 0
        private set

    init {
        if (outletReportCard.unaccountedFor >= 0 && dispatchesReportCard.unaccounted >= 0) {
            totalCollections = outletReportCard.handovers + dispatchesReportCard.handovers
        }
    }
}
