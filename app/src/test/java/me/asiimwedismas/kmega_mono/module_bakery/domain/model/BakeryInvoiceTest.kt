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

    private val bun = BakeryProduct(
        "Bun",
        6,
        75,
        250,
        1_000,
        1_000,
        172_475,
        65_700,
        900
    )

    lateinit var invoice: BakeryInvoice

    @Before
    fun setUp() {
        val bun = BakeryInvoiceItem("Bun", 1).also {
            it.calculateSalesAndProfits(bun)
        }
        val bread = BakeryInvoiceItem("Bread", 1).also {
            it.calculateSalesAndProfits(bread)
        }

        invoice = BakeryInvoice(
            items_list = listOf(bun, bread)
        )
    }

    @Test
    fun calculateTotalFactoryProfit() {
        Truth.assertThat(invoice.totalFactoryProfit).isEqualTo(1_783)
    }

    @Test
    fun calculateTotalOutletProfit() {
        Truth.assertThat(invoice.totalOutletProfit).isEqualTo(1_983)
    }

    @Test
    fun calculateTotalAgentProfit() {
        Truth.assertThat(invoice.totalAgentProfit).isEqualTo(1_483)
    }

    @Test
    fun calculateTotalTotalAgentSale() {
        Truth.assertThat(invoice.totalAgentSale).isEqualTo(4_700)
    }

    @Test
    fun calculateTotalFactorySale() {
        Truth.assertThat(invoice.totalFactorySale).isEqualTo(5_000)
    }

    @Test
    fun calculateTotalOutletSale() {
        Truth.assertThat(invoice.totalOutletSale).isEqualTo(5_200)
    }
}