package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductionRepositoryImpTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var repository: ProductionRepository

    private val sheet0 = FactoryProductionSheet(date = "sheet0", utc = 0)
    private val sheet1 = FactoryProductionSheet(date = "sheet1", utc = 1)
    private val sheet2 = FactoryProductionSheet(date = "sheet2", utc = 2)
    private val sheet3 = FactoryProductionSheet(date = "sheet3", utc = 3)
    private val sheet4 = FactoryProductionSheet(date = "sheet4", utc = 4)

    @Before
    fun setUp() {
        try {
            firestore = Firebase.firestore
            firestore.useEmulator("10.0.2.2", 8080)
            firestore.firestoreSettings = firestoreSettings {
                isPersistenceEnabled = false
            }
        } catch (e: IllegalStateException) {
        }

        hiltRule.inject()
    }

    @After
    fun tearDown() {
        firestore.collection("v2").document("bakery").delete()
    }

    private suspend fun insertSheets() {
        repository.saveProductionSheet(sheet0)
        repository.saveProductionSheet(sheet1)
        repository.saveProductionSheet(sheet2)
        repository.saveProductionSheet(sheet3)
        repository.saveProductionSheet(sheet4)
    }

    @Test
    fun saveProductionSheet() = runTest {
        repository.saveProductionSheet(sheet1)

        val sheet = repository.getProductionSheetForDate("sheet1")
        Truth.assertThat(sheet).isEqualTo(sheet1)
    }

    @Test
    fun getProductionSheetForDate() = runTest {
        insertSheets()

        val sheet = repository.getProductionSheetForDate("sheet1")
        Truth.assertThat(sheet).isEqualTo(sheet1)
    }

    @Test
    fun getProductionSheetsInRange() = runTest {
        insertSheets()

        val sheet = repository.getProductionSheetsInRange(1, 3)
        Truth.assertThat(sheet).containsExactly(sheet1, sheet2, sheet3)
    }

    @Test
    fun returnEmptyList() = runTest {
        insertSheets()

        val sheet = repository.getProductionSheetsInRange(6, 7)
        Truth.assertThat(sheet).isEmpty()
    }
}