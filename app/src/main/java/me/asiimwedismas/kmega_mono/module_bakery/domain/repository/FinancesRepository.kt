package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.CashierStanding
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FieldExpenditure
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletsDaySheet

interface FinancesRepository {
    suspend fun getSalesmenHandoversForDate(date: String): List<CashierStanding>
    suspend fun getSalesmenFieldExpendituresForDate(date: String): List<FieldExpenditure>

    suspend fun getOutletsDaySheetForDate(date: String) : OutletsDaySheet
}