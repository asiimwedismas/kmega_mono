package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class CashierStanding(
    var document_id: String? = null,
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var utc: Long = 0,
    var date: String? = null,
    var cashier_name: String = "",
    var cashier_id: String = "",
    var actual_sales_ed: Int = 0,
    var actual_sales_mn: Int = 0,
    var debit_sales_ed: Int = 0,
    var debit_sales_mn: Int = 0,
    var debits_paid_ed: Int = 0,
    var debits_paid_mn: Int = 0,
    var balance_ed: Int = 0,
    var balance_mn: Int = 0,
    var handover_ed: Int = 0,
    var handover_mn: Int = 0,
    var variance_ed: Int = 0,
    var variance_mn: Int = 0,
){
    fun calculateExpectedBalance() {
        balance_ed = actual_sales_ed - debit_sales_ed + debits_paid_ed
        balance_mn = actual_sales_mn - debit_sales_mn + debits_paid_mn
    }

    fun calculateVariance() {
        variance_ed = handover_ed - balance_ed
        variance_mn = handover_mn - balance_mn
    }
}

