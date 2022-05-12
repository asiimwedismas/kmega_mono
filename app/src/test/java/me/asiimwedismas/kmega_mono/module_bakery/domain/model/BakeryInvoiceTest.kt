package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import com.google.common.truth.Truth

import org.junit.Before
import org.junit.Test

class BakeryInvoiceTest {

    private val bread = BakeryProduct(
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
    lateinit var invoice: BakeryInvoice

    @Before
    fun setUp() {
        val bread = BakeryInvoiceItem(bread, 1)

        invoice = BakeryInvoice(
            items = mutableListOf(bread, bread)
        )
    }

    @Test
    fun calculateTotalFactoryProfit() {
        Truth.assertThat(invoice.totalFactoryProfitGross).isEqualTo(3096)
    }

    @Test
    fun calculateTotalOutletProfit() {
        Truth.assertThat(invoice.totalOutletProfitGross).isEqualTo(3496)
    }

    @Test
    fun calculateTotalAgentProfit() {
        Truth.assertThat(invoice.totalAgentProfitGross).isEqualTo(2696)
    }

    @Test
    fun calculateTotalTotalAgentSale() {
        Truth.assertThat(invoice.totalAgentSale).isEqualTo(7600)
    }

    @Test
    fun calculateTotalFactorySale() {
        Truth.assertThat(invoice.totalFactorySale).isEqualTo(8000)
    }

    @Test
    fun calculateTotalOutletSale() {
        Truth.assertThat(invoice.totalOutletSale).isEqualTo(8400)
    }

    @Test
    fun calculateTotalFactoryProfitNet() {
        Truth.assertThat(invoice.totalFactoryProfitNet).isEqualTo(1270)
    }

    @Test
    fun calculateTotalOutletProfitNet() {
        Truth.assertThat(invoice.totalOutletProfitNet).isEqualTo(1670)
    }

    @Test
    fun calculateTotalAgentProfitNet() {
        Truth.assertThat(invoice.totalAgentProfitNet).isEqualTo(870)
    }
}