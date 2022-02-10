package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.DispatchesRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import java.lang.IllegalStateException
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DispatchesRepositoryImpTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var repository: DispatchesRepository

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
        firestore.clearPersistence()
    }

    @Test
    fun saveDispatch() = runTest {
        val invoiceToday1 = BakeryInvoice(
            date = "date1",
            utc = 1
        )

        repository.saveDispatch(invoiceToday1)

        val result = repository.getDispatchesForDate("date1")

        Truth.assertThat(result).containsExactly(invoiceToday1)
    }

    @Test
    fun dispatchesForDate() = runTest {
        val invoiceToday1 = BakeryInvoice(date = "today", utc = 1)
        val invoiceToday2 = BakeryInvoice(date = "today", utc = 2)
        val invoiceMorrow1 = BakeryInvoice(date = "2morrow", utc = 3)
        val invoiceToday3 = BakeryInvoice(date = "today", utc = 4)

        repository.saveDispatch(invoiceToday1)
        repository.saveDispatch(invoiceToday2)
        repository.saveDispatch(invoiceMorrow1)
        repository.saveDispatch(invoiceToday3)

        val result = repository.getDispatchesForDate("today")

        Truth.assertThat(result).containsExactly(invoiceToday1, invoiceToday2, invoiceToday3)
    }

    @Test
    fun dispatchesForRange() = runTest {
        val invoiceToday1 = BakeryInvoice(date = "today", utc = 1)
        val invoiceToday2 = BakeryInvoice(date = "today", utc = 2)
        val invoiceMorrow1 = BakeryInvoice(date = "2morrow", utc = 3)
        val invoiceToday3 = BakeryInvoice(date = "today", utc = 4)
        val invoiceMorrow2 = BakeryInvoice(date = "today", utc = 4, salesman_id = "dismas")

        repository.saveDispatch(invoiceToday1)
        repository.saveDispatch(invoiceToday2)
        repository.saveDispatch(invoiceMorrow1)
        repository.saveDispatch(invoiceToday3)
        repository.saveDispatch(invoiceMorrow2)

        val result = repository.getDispatchesForRange(2, 4)

        Truth.assertThat(result)
            .containsExactly(invoiceToday2, invoiceMorrow1, invoiceToday3, invoiceMorrow2)
    }

    @Test
    fun dispatchesForSalesmanForDate() = runTest {
        val invoiceDismas = BakeryInvoice(date = "today", utc = 1, salesman_id = "Dismas")
        val invoiceAnitah = BakeryInvoice(date = "today", utc = 1, salesman_id = "Anitah")
        val invoiceRodgers = BakeryInvoice(date = "today", utc = 1, salesman_id = "Rodgers")
        val invoiceDismas2 = BakeryInvoice(date = "today", utc = 1, salesman_id = "Dismas")
        val invoiceDismas3 = BakeryInvoice(date = "yesterday", utc = 1, salesman_id = "Dismas")

        repository.saveDispatch(invoiceDismas)
        repository.saveDispatch(invoiceAnitah)
        repository.saveDispatch(invoiceRodgers)
        repository.saveDispatch(invoiceDismas2)
        repository.saveDispatch(invoiceDismas3)

        val result = repository.getDispatchesForSalesmanForDate(salesmanID = "Dismas", date = "today")

        Truth.assertThat(result).containsExactly(invoiceDismas, invoiceDismas2)
    }

    @Test
    fun dispatchesForSalesmanForRange() = runTest {
        val invoiceDismas = BakeryInvoice(date = "today", utc = 1, salesman_id = "Dismas")
        val invoiceAnitah = BakeryInvoice(date = "today", utc = 2, salesman_id = "Anitah")
        val invoiceRodgers = BakeryInvoice(date = "today", utc = 3, salesman_id = "Rodgers")
        val invoiceDismas2 = BakeryInvoice(date = "today", utc = 2, salesman_id = "Dismas")
        val invoiceDismas3 = BakeryInvoice(date = "yesterday", utc = 4, salesman_id = "Dismas")

        repository.saveDispatch(invoiceDismas)
        repository.saveDispatch(invoiceAnitah)
        repository.saveDispatch(invoiceRodgers)
        repository.saveDispatch(invoiceDismas2)
        repository.saveDispatch(invoiceDismas3)

        val result = repository.getDispatchesForSalesmanForRange("Dismas", 2,4)


        Truth.assertThat(result).containsExactly(invoiceDismas3, invoiceDismas2)
    }
}