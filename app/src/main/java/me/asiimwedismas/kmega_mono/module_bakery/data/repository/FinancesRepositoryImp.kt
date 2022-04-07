package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.CashierStanding
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FieldExpenditure
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletsDaySheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionsSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository

class FinancesRepositoryImp(
    private val salesmenReference: CollectionReference,
    private val outletsReference: CollectionReference,
    private val fieldExpenditureReference: CollectionReference,
    private val safeTransactionsReference: CollectionReference,
) : FinancesRepository {
    override suspend fun getSalesmenHandoversForDate(date: String): List<CashierStanding> {
        return salesmenReference
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(CashierStanding::class.java)
    }

    override suspend fun getSalesmenHandoversInRange(from: Long, to: Long): List<CashierStanding> {
        return salesmenReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .orderBy("utc")
            .get()
            .await()
            .toObjects(CashierStanding::class.java)
    }

    override suspend fun getSalesmenFieldExpendituresForDate(date: String): List<FieldExpenditure> {
        return fieldExpenditureReference
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(FieldExpenditure::class.java)
    }

    override suspend fun getOutletsDaySheetForDate(date: String): OutletsDaySheet {
        return outletsReference
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(OutletsDaySheet::class.java)
            .firstOrNull() ?: OutletsDaySheet()
    }

    override suspend fun deleteSafeTransactionSheet(sheetID: String) {
        safeTransactionsReference.document(sheetID).delete().await()
    }

    override suspend fun saveSafeTransactionSheet(safeTransactionsSheet: SafeTransactionsSheet) {
        if (safeTransactionsSheet.document_id.isEmpty()) {
            safeTransactionsSheet.document_id = safeTransactionsSheet.date
        }

        safeTransactionsReference.document(safeTransactionsSheet.document_id).set(safeTransactionsSheet)
            .await()
    }

    override suspend fun getSafeTransactionSheetForDate(date: String): SafeTransactionsSheet {
        return safeTransactionsReference
            .document(date)
            .get()
            .await()
            .toObject(SafeTransactionsSheet::class.java) ?: SafeTransactionsSheet()
    }

    override suspend fun getSafeTransactionSheetsInRange(from: Long, to: Long): List<SafeTransactionsSheet> {
        return safeTransactionsReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .orderBy("utc")
            .get()
            .await()
            .toObjects(SafeTransactionsSheet::class.java)
    }

}