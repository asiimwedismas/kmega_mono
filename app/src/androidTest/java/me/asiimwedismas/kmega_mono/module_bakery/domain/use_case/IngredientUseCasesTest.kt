package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.getOrAwaitValue
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.IngredientDao
import me.asiimwedismas.kmega_mono.module_bakery.data.repository.IngredientRepositoryImp
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

//@SmallTest
//@HiltAndroidTest
//@ExperimentalCoroutinesApi
//class IngredientUseCasesTest {
//
//    @get:Rule
//    var instantTaskExecutor = InstantTaskExecutorRule()
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    @Named("test_db")
//    lateinit var database: BakeryDatabase
//
//    private lateinit var dao: IngredientDao
//    private lateinit var repo: IngredientRepository
//    private lateinit var useCases: IngredientUseCases
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//
//        dao = database.ingredientDao()
//        repo = IngredientRepositoryImp(dao)
//
//        useCases = IngredientUseCases(
//            InsertIngredients(repo),
//            UpdateIngredients(repo),
//            DeleteIngredients(repo),
//            GetAllIngredients(repo)
//        )
//    }
//
//    @After
//    fun tearDown() {
//        database.close()
//    }
//
//    @Test
//    fun insertIngredients() = runBlockingTest {
//        val ingredient = BakeryIngredient(
//            "flour",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        val ingredient2 = BakeryIngredient(
//            "sugar",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        useCases.insertIngredients(listOf(ingredient, ingredient2))
//
//        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
//        Truth.assertThat(allIngredients).containsExactly(ingredient, ingredient2)
//    }
//
//    @Test
//    fun updateIngredients() = runBlockingTest {
//        val flour = BakeryIngredient(
//            "flour",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        val sugar = BakeryIngredient(
//            "sugar",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        val salt = BakeryIngredient(
//            "salt",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        useCases.insertIngredients(listOf(flour, sugar, salt))
//
//        val updatedFlour = BakeryIngredient(
//            "flour",
//            2,
//            2,
//            "changed",
//            2
//        )
//
//        val updatedSalt = BakeryIngredient(
//            "salt",
//            2,
//            3,
//            "litres",
//            4
//        )
//
//        val updatedUnkown = BakeryIngredient(
//            "unknown",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        useCases.updateIngredients(listOf(updatedFlour, updatedSalt, updatedUnkown))
//
//        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
//        Truth.assertThat(allIngredients).containsExactly(updatedFlour, updatedSalt, sugar)
//
//    }
//
//    @Test
//    fun deleteIngredients() = runBlockingTest {
//        val flour = BakeryIngredient(
//            "flour",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        val sugar = BakeryIngredient(
//            "sugar",
//            2,
//            2,
//            "changed",
//            2
//        )
//
//        val salt = BakeryIngredient(
//            "salt",
//            2,
//            2,
//            "changed",
//            2
//        )
//
//        useCases.insertIngredients(listOf(flour, sugar, salt))
//
//        dao.delete(listOf(flour, salt))
//
//        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
//        Truth.assertThat(allIngredients).containsExactly(sugar)
//    }
//
//    @Test
//    fun getAllIngredients() = runBlockingTest {
//        val flour = BakeryIngredient(
//            "flour",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        val sugar = BakeryIngredient(
//            "sugar",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        val mangoes = BakeryIngredient(
//            "mangoes",
//            1,
//            1,
//            "litres",
//            1
//        )
//
//        useCases.insertIngredients(listOf(flour, sugar, mangoes))
//
//        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
//        Truth.assertThat(allIngredients).containsExactly(flour, mangoes, sugar)
//    }
//}