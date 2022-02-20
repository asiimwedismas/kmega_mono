package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ExpiredRepository

class ExpiredRepositoryImp(
    private val collectionReference: CollectionReference,
) : ExpiredRepository {
    override suspend fun saveExpired(invoice: BakeryInvoice) {
        if (invoice.document_id.isEmpty()) {
            invoice.document_id = collectionReference.document().id
        }

        collectionReference.document(invoice.document_id).set(invoice).await()
    }

    override suspend fun getExpiredForSalesmanForDate(
        salesmanID: String,
        date: String,
    ): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("salesman_id", salesmanID)
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getExpiredForOutletForDate(
        outletID: String,
        date: String,
    ): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("outlet_id", outletID)
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getExpiredForFactoryForDate(date: String): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("outlet_id", "FACTORY")
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getExpiredForFieldForDate(date: String): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("agent_id", "")
            .whereEqualTo("outlet_id", "")
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }


    override suspend fun getAllExpiredInRange(from: Long, to: Long): List<BakeryInvoice> {
        return collectionReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }
}