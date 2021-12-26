package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import me.asiimwedismas.bakery_module.domain.model.BakeryInvoice
import me.asiimwedismas.bakery_module.domain.model.BakeryInvoiceItem
import me.asiimwedismas.bakery_module.domain.model.BakeryProduct
import me.asiimwedismas.bakery_module.domain.model.calculateSalesAndProfits
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class BakeryInvoiceTest {

    val bread = BakeryProduct(
        "Bread",
        1,
        99.0,
        72.0,
        4_000.0,
        4_200.0,
        169_410.0,
        65_700.0,
        3_800.0
    )

    val bun = BakeryProduct(
        "Bun",
        6,
        75.0,
        250.0,
        1_000.0,
        1_000.0,
        172_475.0,
        65_700.0,
        900.0
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
        assertEquals("factory profits ", 1_783.0, invoice.calculateTotalFactoryProfit(), 0.2)
    }

    @Test
    fun calculateTotalOutletProfit() {
        assertEquals("outlet profits ", 1_983.0, invoice.calculateTotalOutletProfit(), 0.2)
    }

    @Test
    fun calculateTotalAgentProfit() {
        assertEquals("agent profits ", 1_483.0, invoice.calculateTotalAgentProfit(), 0.2)
    }

    @Test
    fun calculateTotalTotalAgentSale() {
        assertEquals("agent sales ", 4_700.0, invoice.calculateTotalTotalAgentSale(), 0.2)
    }

    @Test
    fun calculateTotalFactorySale() {
        assertEquals("factory sales ", 5_000.0, invoice.calculateTotalFactorySale(), 0.2)
    }

    @Test
    fun calculateTotalOutletSale() {
        assertEquals("outlet sales ", 5_200.0, invoice.calculateTotalOutletSale(), 0.2)
    }
}