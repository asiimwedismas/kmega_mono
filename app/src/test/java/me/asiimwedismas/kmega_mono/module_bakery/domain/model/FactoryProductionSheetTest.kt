package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class FactoryProductionSheetTest {
    val bread = BakeryProduct(
        "Bread",
        1,
        99,
        72,
        4_000,
        4_200,
        169_410,
        65_700,
        3_800
    )

    val item = FactoryProductionItem(bread, 1)
    val productionSheet = FactoryProductionSheet(items = mutableListOf(item, item))

    @Test
    fun `item buvera are calculated properly`() {
        Truth.assertThat(productionSheet.totalBuvera).isEqualTo(198)
    }

    @Test
    fun `item ingredient_costs are calculated properly`() {
        Truth.assertThat(productionSheet.totalIngredientCosts).isEqualTo(4704)
    }

    @Test
    fun `item fixed_costs are calculated properly`() {
        Truth.assertThat(productionSheet.totalFixedCosts).isEqualTo(1824)
    }

    @Test
    fun `item wholesale_sales are calculated properly`() {
        Truth.assertThat(productionSheet.totalWholeSales).isEqualTo(8000)
    }

    @Test
    fun `item profit_gross are calculated properly`() {
        Truth.assertThat(productionSheet.totalGrossProfit).isEqualTo(3096)
    }

    @Test
    fun `item profit_net are calculated properly`() {
        Truth.assertThat(productionSheet.totalNetProfit).isEqualTo(1270)
    }
}