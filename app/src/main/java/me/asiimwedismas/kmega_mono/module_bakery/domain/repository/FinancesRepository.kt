package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.CashierStanding
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FieldExpenditure
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletsDaySheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionsSheet

interface FinancesRepository {
    suspend fun getSalesmenHandoversForDate(date: String): List<CashierStanding>
    suspend fun getSalesmenFieldExpendituresForDate(date: String): List<FieldExpenditure>

    suspend fun getOutletsDaySheetForDate(date: String): OutletsDaySheet

    /*Safe transactions*/
    suspend fun deleteSafeTransactionSheet(sheetID: String)
    suspend fun saveSafeTransactionSheet(safeTransactionsSheet: SafeTransactionsSheet)
    suspend fun getSafeTransactionSheetForDate(date: String): SafeTransactionsSheet
    suspend fun getSafeTransactionSheetsInRange(from: Long, to: Long): List<SafeTransactionsSheet>
}