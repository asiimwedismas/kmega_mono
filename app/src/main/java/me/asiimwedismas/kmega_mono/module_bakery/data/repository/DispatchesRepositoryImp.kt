package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.DispatchesRepository

class DispatchesRepositoryImp(
    private val collectionReference: CollectionReference,
) : DispatchesRepository {

    override suspend fun delete(invoiceID: String) {
        collectionReference.document(invoiceID).delete().await()
    }

    override suspend fun saveDispatch(invoice: BakeryInvoice) {
        if (invoice.document_id.isEmpty()) {
            invoice.document_id = collectionReference.document().id
        }

        collectionReference.document(invoice.document_id).set(invoice).await()
    }

    override suspend fun getDispatchesForDate(date: String): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getDispatchesForRange(from: Long, to: Long): List<BakeryInvoice> {
        return collectionReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getDispatchesForSalesmanForDate(
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

    override suspend fun getDispatchesForSalesmanForRange(
        salesmanID: String,
        from: Long,
        to: Long,
    ): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("salesman_id", salesmanID)
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }
}