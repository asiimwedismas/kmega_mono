package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.AuditRepository

class AuditRepositoryImp(
    private val collectionReference: CollectionReference,
) : AuditRepository {
    override suspend fun delete(invoiceID: String) {
        collectionReference.document(invoiceID).delete().await()
    }

    override suspend fun saveAudit(invoice: BakeryInvoice) {
        if (invoice.document_id.isEmpty()) {
            invoice.document_id = collectionReference.document().id
        }

        collectionReference.document(invoice.document_id).set(invoice).await()
    }

    override suspend fun getAuditForOutletForDate(outletID: String, date: String): BakeryInvoice {
        return collectionReference
            .whereEqualTo("outlet_id", outletID)
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
            .getOrElse(0) { BakeryInvoice() }
    }

    override suspend fun getAuditForFactoryForDate(date: String): BakeryInvoice {
        return collectionReference
            .whereEqualTo("outlet_id", "FACTORY")
            .whereEqualTo("date", date)
            .get()
            .await()
            .toObjects(BakeryInvoice::class.java)
            .firstOrNull() ?: BakeryInvoice()
    }
}