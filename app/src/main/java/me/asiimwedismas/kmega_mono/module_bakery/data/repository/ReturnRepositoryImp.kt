package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ReturnRepository

class ReturnRepositoryImp(
    private val collectionReference: CollectionReference,
) : ReturnRepository {
    override suspend fun saveReturn(invoice: BakeryInvoice) {
        if (invoice.document_id.isEmpty()) {
            invoice.document_id = collectionReference.document().id
        }

        collectionReference.document(invoice.document_id).set(invoice).await()
    }

    override suspend fun getReturnForSalesmanForDate(
        salesmanID: String,
        date: String,
    ): BakeryInvoice {
        return collectionReference
            .whereEqualTo("salesman_id", salesmanID)
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
            .getOrElse(0){ BakeryInvoice() }

    }

    override suspend fun getReturnsForDate(date: String): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getReturnsInRange(from: Long, to: Long): List<BakeryInvoice> {
        return collectionReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }
}