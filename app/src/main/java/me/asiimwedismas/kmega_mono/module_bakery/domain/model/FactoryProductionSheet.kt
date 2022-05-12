package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import java.util.ArrayList

data class FactoryProductionSheet(
    var date: String = "",
    var document_id: String = "",
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var utc: Long = 0,
    var lock_status: Boolean = false,
    var items: MutableList<FactoryProductionItem> = ArrayList(),
)

val FactoryProductionSheet.totalBuvera
    get() = items.sumOf { it.buvera }

val FactoryProductionSheet.totalIngredientCosts
    get() = items.sumOf { it.ingredient_costs }

val FactoryProductionSheet.totalFixedCosts
    get() = items.sumOf { it.fixed_costs }

val FactoryProductionSheet.totalWholeSales
    get() = items.sumOf { it.wholesale_sales }

val FactoryProductionSheet.totalGrossProfit
    get() = items.sumOf { it.profit_gross }

val FactoryProductionSheet.totalNetProfit
    get() = items.sumOf{it.profit_net}