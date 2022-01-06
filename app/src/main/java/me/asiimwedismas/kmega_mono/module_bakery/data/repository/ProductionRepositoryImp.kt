package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.ktx.Firebase
import dagger.Component
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionSheet
import me.asiimwedismas.bakery_module.other.Constants
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import javax.inject.Inject
import javax.inject.Named

class ProductionRepositoryImp(
    private val collectionReference: CollectionReference,
) : ProductionRepository {

    override suspend fun saveProductionSheet(productionSheet: FactoryProductionSheet) {
        if (productionSheet.document_id.isEmpty()) {
            productionSheet.document_id = productionSheet.date
        }

        productionSheet.document_id.ifEmpty { productionSheet.date }

        collectionReference.document(productionSheet.document_id)
            .set(productionSheet).await()
    }

    override suspend fun getProductionSheetForDate(date: String): FactoryProductionSheet? {
        return collectionReference.document(date)
            .get().await().toObject(FactoryProductionSheet::class.java)
    }

    override suspend fun getProductionSheetsInRange(
        from: Long,
        to: Long,
    ): List<FactoryProductionSheet> {

        val result = collectionReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .orderBy("utc")
            .get()
            .await()

        return result!!.toObjects(FactoryProductionSheet::class.java)
    }
}