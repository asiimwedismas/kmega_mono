package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class SafeTransactionsSheet(
    var date: String = "",
    var document_id: String = "",
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var utc: Long = 0,
    var lock_status: Boolean = false,
    var items: MutableList<SafeTransactionItem> = ArrayList(),
)

val SafeTransactionsSheet.totalTransactions
    get() = items.sumOf { it.amount }

val SafeTransactionsSheet.totalIngredientsTransactions
    get() = items.sumOf { if (it.isIngredient) it.amount else 0 }

val SafeTransactionsSheet.totalOtherTransactions
    get() = items.sumOf { if (!it.isIngredient) it.amount else 0 }


