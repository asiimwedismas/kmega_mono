package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import me.asiimwedismas.bakery_module.domain.model.BakeryInvoiceItem
import me.asiimwedismas.bakery_module.domain.model.BakeryProduct
import me.asiimwedismas.bakery_module.domain.model.calculateSalesAndProfits
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class BakeryInvoiceItemTest {

    lateinit var bread: BakeryProduct
    lateinit var breadInvoiceItem: BakeryInvoiceItem

    @Before
    fun setUp(){
        bread = BakeryProduct(
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

        breadInvoiceItem = BakeryInvoiceItem(
            "Bread",
            1
        )

        breadInvoiceItem.calculateSalesAndProfits(bread)
    }

    @Test
    fun `factory sales are calculated properly`() {
        assertEquals("factory sales",4000.0,breadInvoiceItem.total_factory_sale,0.1)
    }

    @Test
    fun `factory profits are calculated properly`() {
        assertEquals("factory profits",1_548.0,breadInvoiceItem.factory_profit,0.1)
    }

    @Test
    fun `outlet sales are calculated properly`() {
        assertEquals("outlet sales",4_200.0,breadInvoiceItem.total_outlet_sale,0.1)
    }

    @Test
    fun `outlet profits are calculated properly`() {
        assertEquals("outlet profits",1_748.0,breadInvoiceItem.outlet_profit,0.1)
    }

    @Test
    fun `agent sales are calculated properly`() {
        assertEquals("agent sales", 3_800.0, breadInvoiceItem.total_agent_sale, 0.1)
    }

    @Test
    fun `agent profits are calculated properly`() {
        assertEquals("agent profits", 1_348.0, breadInvoiceItem.agent_profit, 0.1)
    }
}