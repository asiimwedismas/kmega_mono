package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.asiimwedismas.bakery_module.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.getOrAwaitValue
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.IngredientDao
import me.asiimwedismas.kmega_mono.module_bakery.data.repository.IngredientRepositoryImp
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class IngredientUseCasesTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: BakeryDatabase

    private lateinit var dao: IngredientDao
    private lateinit var repo: IngredientRepository
    private lateinit var useCases: IngredientUseCases

    @Before
    fun setUp() {
        hiltRule.inject()

        dao = database.ingredientDao()
        repo = IngredientRepositoryImp(dao)

        useCases = IngredientUseCases(
            InsertIngredients(repo),
            UpdateIngredients(repo),
            DeleteIngredients(repo),
            GetAllIngredients(repo)
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertIngredients() = runBlockingTest {
        val ingredient = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val ingredient2 = BakeryIngredient(
            "sugar",
            1.0,
            1.0,
            "litres",
            1.0
        )

        useCases.insertIngredients(ingredient, ingredient2)

        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
        Truth.assertThat(allIngredients).containsExactly(ingredient, ingredient2)
    }

    @Test
    fun updateIngredients() = runBlockingTest {
        val flour = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val sugar = BakeryIngredient(
            "sugar",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val salt = BakeryIngredient(
            "salt",
            1.0,
            1.0,
            "litres",
            1.0
        )

        useCases.insertIngredients(flour, sugar, salt)

        val updatedFlour = BakeryIngredient(
            "flour",
            2.0,
            2.0,
            "changed",
            2.0
        )

        val updatedSalt = BakeryIngredient(
            "salt",
            2.0,
            3.0,
            "litres",
            4.0
        )

        val updatedUnkown = BakeryIngredient(
            "unknown",
            1.0,
            1.0,
            "litres",
            1.0
        )

        useCases.updateIngredients(updatedFlour, updatedSalt, updatedUnkown)

        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
        Truth.assertThat(allIngredients).containsExactly(updatedFlour, updatedSalt, sugar)

    }

    @Test
    fun deleteIngredients() = runBlockingTest {
        val flour = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val sugar = BakeryIngredient(
            "sugar",
            2.0,
            2.0,
            "changed",
            2.0
        )

        val salt = BakeryIngredient(
            "salt",
            2.0,
            2.0,
            "changed",
            2.0
        )

        useCases.insertIngredients(flour, sugar, salt)

        dao.delete(flour, salt)

        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
        Truth.assertThat(allIngredients).containsExactly(sugar)
    }

    @Test
    fun getAllIngredients() = runBlockingTest {
        val flour = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val sugar = BakeryIngredient(
            "sugar",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val mangoes = BakeryIngredient(
            "mangoes",
            1.0,
            1.0,
            "litres",
            1.0
        )

        useCases.insertIngredients(flour, sugar, mangoes)

        val allIngredients = useCases.getAllIngredients().getOrAwaitValue()
        Truth.assertThat(allIngredients).containsExactly(flour, mangoes, sugar)
    }
}