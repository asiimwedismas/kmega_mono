package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import org.junit.Assert.*
import org.junit.Test

class FactoryProductionItemTest {
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

    val item = FactoryProductionItem(bread, 2)

    @Test
    fun `item buvera are calculated properly`() {
        assertThat(item.buvera).isEqualTo(198)
    }

    @Test
    fun `item ingredient_costs are calculated properly`() {
        assertThat(item.ingredient_costs).isEqualTo(4704)
    }

    @Test
    fun `item fixed_costs are calculated properly`() {
        assertThat(item.fixed_costs).isEqualTo(1824)
    }

    @Test
    fun `item wholesale_sales are calculated properly`() {
        assertThat(item.wholesale_sales).isEqualTo(8000)
    }

    @Test
    fun `item profit_gross are calculated properly`() {
        assertThat(item.profit_gross).isEqualTo(3096)
    }

    @Test
    fun `item profit_net are calculated properly`() {
        assertThat(item.profit_net).isEqualTo(1270)
    }
}