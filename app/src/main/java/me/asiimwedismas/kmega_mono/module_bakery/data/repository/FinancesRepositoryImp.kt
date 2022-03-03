package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.CashierStanding
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FieldExpenditure
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletsDaySheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository

class FinancesRepositoryImp(
    private val salesmenReference: CollectionReference,
    private val outletsReference: CollectionReference,
    private val fieldExpenditureReference: CollectionReference,
) : FinancesRepository {
    override suspend fun getSalesmenHandoversForDate(date: String): List<CashierStanding> {
        return salesmenReference
            .whereEqualTo("date", date)
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

}