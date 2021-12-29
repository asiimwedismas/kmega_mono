package me.asiimwedismas.bakery_module.data.local.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.asiimwedismas.bakery_module.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class IngredientDaoTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: BakeryDatabase

    private lateinit var dao: IngredientDao

    @Before
    fun setUp() {
        hiltRule.inject()

        dao = database.ingredientDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_with_single_argument() = runBlockingTest {
        val ingredient = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )

        dao.insert(ingredient)

        val allIngredients = dao.getAllIngredients().getOrAwaitValue()

        assertThat(allIngredients).contains(ingredient);
    }

    @Test
    fun insert_with_varargs() = runBlockingTest {
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

        dao.insert(ingredient, ingredient2)

        val allIngredients = dao.getAllIngredients().getOrAwaitValue()

        assertThat(allIngredients).containsExactly(ingredient, ingredient2);
    }

    @Test
    fun update() = runBlockingTest {
        val ingredient = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )
        dao.insert(ingredient)

        val ingredient2 = BakeryIngredient(
            "flour",
            2.0,
            2.0,
            "changed",
            2.0
        )

        dao.update(ingredient2)

        val allIngredients = dao.getAllIngredients().getOrAwaitValue()

        assertThat(allIngredients).containsExactly(ingredient2)

    }

    @Test
    fun delete() = runBlockingTest {
        val ingredient = BakeryIngredient(
            "flour",
            1.0,
            1.0,
            "litres",
            1.0
        )

        val ingredient2 = BakeryIngredient(
            "sugar",
            2.0,
            2.0,
            "changed",
            2.0
        )

        dao.insert(ingredient, ingredient2)

        dao.delete(ingredient)

        val allIngredients = dao.getAllIngredients().getOrAwaitValue()
        assertThat(allIngredients).containsExactly(ingredient2)
    }

    @Test
    fun getAllIngredients() = runBlockingTest {
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

        val ingredient3 = BakeryIngredient(
            "mangoes",
            1.0,
            1.0,
            "litres",
            1.0
        )

        dao.insert(ingredient, ingredient2, ingredient3)

        val allIngredients = dao.getAllIngredients().getOrAwaitValue()

        assertThat(allIngredients).containsExactly(ingredient, ingredient3, ingredient2);
    }
}