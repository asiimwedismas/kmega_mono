package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.UsedIngredientsSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.UsedIngredientsRepository

class UsedIngredientsRepositoryImpl(
    private val collectionReference: CollectionReference,
) : UsedIngredientsRepository {
    override suspend fun delete(sheetID: String) {
        collectionReference.document(sheetID).delete().await()
    }

    override suspend fun save(usedIngredientsSheet: UsedIngredientsSheet) {
        if (usedIngredientsSheet.document_id.isEmpty()) {
            usedIngredientsSheet.document_id = usedIngredientsSheet.date
        }

        collectionReference.document(usedIngredientsSheet.document_id).set(usedIngredientsSheet)
            .await()
    }

    override suspend fun getSheetForDate(date: String): UsedIngredientsSheet {
        return collectionReference
            .document(date)
            .get()
            .await()
            .toObject(UsedIngredientsSheet::class.java) ?: UsedIngredientsSheet()
    }

    override suspend fun getSheetsInRange(
        from: Long,
        to: Long,
    ): List<UsedIngredientsSheet> {
        val result = collectionReference
            .whereGreaterThanOrEqualTo("utc", from)
            .whereLessThanOrEqualTo("utc", to)
            .orderBy("utc")
            .get()
            .await()

        return result!!.toObjects(UsedIngredientsSheet::class.java)
    }
}