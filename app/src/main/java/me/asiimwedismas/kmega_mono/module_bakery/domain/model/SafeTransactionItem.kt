package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class SafeTransactionItem(
    var category: String = "",
    var explanation: String = "",
    var isIngredient: Boolean = false,
    var amount: Int = 0,
)