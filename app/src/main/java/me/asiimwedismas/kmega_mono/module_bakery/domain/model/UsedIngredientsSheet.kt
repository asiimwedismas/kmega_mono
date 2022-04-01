package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class UsedIngredientsSheet(
    var date: String = "",
    var document_id: String = "",
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var utc: Long = 0,
    var lock_status: Boolean = false,
    var items: MutableList<UsedIngredientItem> = ArrayList(),
)

val UsedIngredientsSheet.totalUsedIngredients
    get() = items.sumOf { it.total_cost }
