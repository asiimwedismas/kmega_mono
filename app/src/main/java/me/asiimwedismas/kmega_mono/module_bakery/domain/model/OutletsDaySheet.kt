package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class OutletsDaySheet(
    var document_id: String? = null,
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var date: String? = null,
    var utc: Long = 0,
    var outletStandingList: MutableList<OutletStanding> = ArrayList(),
)

data class OutletStanding(
    var outlet_id: String? = null,
    var outlet_name: String? = null,
    var brought_forward: Long = 0L,
    var received: Long = 0L,
    var damages: Long = 0L,
    var expenditure: Long = 0L,
    var total_stock_balance: Long = 0L,
    var cash_handover: Long = 0L,
    var expected_stock_balance: Long = 0L,
    var goods_stock_audit: Long = 0L,
    var cash_stock_audit: Long = 0L,
    var audit_shortage_excess: Long = 0L,
    var actual_carry_forward: Long = 0L,
) {
    fun calculate() {
        total_stock_balance = (brought_forward + received)

        val adjustForIdentifier = if (cash_handover < 0) -(cash_handover) else 0

        expected_stock_balance =
            (total_stock_balance - expenditure - (cash_handover + adjustForIdentifier) - damages)

        actual_carry_forward = if (cash_stock_audit == 0L && goods_stock_audit == 0L) {
            (expected_stock_balance)
        } else {
            (cash_stock_audit + goods_stock_audit)
        }
        audit_shortage_excess = (actual_carry_forward - expected_stock_balance)
    }
}
