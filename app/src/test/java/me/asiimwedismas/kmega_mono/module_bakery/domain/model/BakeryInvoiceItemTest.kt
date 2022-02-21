package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before

import org.junit.Test

class BakeryInvoiceItemTest {

    lateinit var bread: BakeryProduct
    lateinit var breadInvoiceItem: BakeryInvoiceItem

    @Before
    fun setUp() {
        bread = BakeryProduct(
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

        breadInvoiceItem = BakeryInvoiceItem(bread, 1)
    }

    @Test
    fun `factory sales are calculated properly`() {
        assertThat(breadInvoiceItem.total_factory_sale).isEqualTo(4000)
    }

    @Test
    fun `factory profits are calculated properly`() {
        assertThat(breadInvoiceItem.factory_profit_gross).isEqualTo(1_548)
    }

    @Test
    fun `outlet sales are calculated properly`() {
        assertThat(breadInvoiceItem.total_outlet_sale).isEqualTo(4_200)
    }

    @Test
    fun `outlet profits are calculated properly`() {
        assertThat(breadInvoiceItem.outlet_profit_gross).isEqualTo(1_748)
    }

    @Test
    fun `agent sales are calculated properly`() {
        assertThat(breadInvoiceItem.total_agent_sale).isEqualTo(3_800)
    }

    @Test
    fun `agent profits are calculated properly`() {
        assertThat(breadInvoiceItem.agent_profit_gross).isEqualTo(1_348)
    }

    @Test
    fun `agent net profits are calculated properly`() {
        assertThat(breadInvoiceItem.agent_profit_net).isEqualTo(435)
    }

    @Test
    fun `factory net profits are calculated properly`() {
        assertThat(breadInvoiceItem.factory_profit_net).isEqualTo(635)
    }

    @Test
    fun `outlet net profits are calculated properly`() {
        assertThat(breadInvoiceItem.outlet_profit_net).isEqualTo(835)
    }
}