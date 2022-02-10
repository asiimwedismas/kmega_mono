package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.OutletRepository

class OutletRepositoryImp(
    private val collectionReference: CollectionReference,
) : OutletRepository {
    override suspend fun saveDelivery(invoice: BakeryInvoice) {
        if (invoice.document_id.isEmpty()) {
            invoice.document_id = collectionReference.document().id
        }

        collectionReference.document(invoice.document_id).set(invoice).await()
    }

    override suspend fun getDeliveriesForDate(date: String): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getDeliveriesInRange(from: Long, to: Long): List<BakeryInvoice> {
        return collectionReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }

    override suspend fun getDeliveriesForOutletDated(
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

    override suspend fun getDeliveriesOfSalesmanDated(
        salesmanID: String,
        date: String,
    ): List<BakeryInvoice> {
        return collectionReference
            .whereEqualTo("salesmand_id", salesmanID)
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
    }
}