package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.filters.SmallTest
//import com.google.common.truth.Truth.assertThat
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.ProductIngredientDao
//import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
//import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
//import me.asiimwedismas.kmega_mono.module_bakery.data.repository.ProductIngredientRepositoryImp
//import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository
//import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product_ingredient.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import javax.inject.Inject
//import javax.inject.Named
//
//@ExperimentalCoroutinesApi
//@SmallTest
//@HiltAndroidTest
//class ProductIngredientUseCasesTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Inject
//    @Named("test_db")
//    lateinit var database: BakeryDatabase
//
//    private lateinit var dao: ProductIngredientDao
//    private lateinit var repo: ProductIngredientRepository
//    private lateinit var useCases: ProductIngredientUseCases
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//
//        dao = database.productIngredientDao()
//        repo = ProductIngredientRepositoryImp(dao)
//
//        useCases = ProductIngredientUseCases(
//            InsertProductIngredients(repo),
//            UpdateProductIngredients(repo),
//            DeleteProductIngredients(repo),
//            GetAllProductIngredients(repo),
//            GetProductsWithIngredient(repo),
//            GetIngredientsForProduct(repo)
//        )
//    }
//
//    @After
//    fun tearDown() {
//        database.close()
//    }
//
//    @Test
//    fun insert() = runBlockingTest {
//        val productIngredient1 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "sugar",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient2 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        useCases.insert(listOf(productIngredient1, productIngredient2))
//
//        val allProductIngredients = useCases.getAllProductIngredients().getOrAwaitValue()
//        assertThat(allProductIngredients).containsExactly(productIngredient1, productIngredient2)
//    }
//
//    @Test
//    fun update() = runBlockingTest {
//        val productIngredient1 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "sugar",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient2 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val updatedProductIngredient2 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "salt",
//            quantity = 7.0,
//            amount = 4000
//        )
//
//        useCases.insert(listOf(productIngredient1, productIngredient2))
//        useCases.update(listOf(updatedProductIngredient2))
//
//        val allProductIngredients = useCases.getAllProductIngredients().getOrAwaitValue()
//        assertThat(allProductIngredients).containsExactly(productIngredient1, updatedProductIngredient2)
//    }
//
//    @Test
//    fun delete() = runBlockingTest {
//        val productIngredient1 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "sugar",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient2 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        useCases.insert(listOf(productIngredient1,productIngredient2))
//        useCases.delete(listOf(productIngredient2))
//
//        val allProductIngredients = useCases.getAllProductIngredients().getOrAwaitValue()
//        assertThat(allProductIngredients).containsExactly(productIngredient1)
//    }
//
//    @Test
//    fun getRowsWithIngredient() = runBlockingTest {
//        val productIngredient1 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "sugar",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient2 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient3 = BakeryProductIngredient(
//            product_name = "muffin",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        useCases.insert(listOf(productIngredient1,productIngredient2, productIngredient3))
//
//        val allProductIngredients = useCases.getProductsWithIngredient("salt").getOrAwaitValue()
//        assertThat(allProductIngredients).containsExactly(productIngredient2, productIngredient3)
//    }
//
//    @Test
//    fun getIngredientsForProduct() = runBlockingTest {
//        val productIngredient1 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "sugar",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient2 = BakeryProductIngredient(
//            product_name = "bread",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        val productIngredient3 = BakeryProductIngredient(
//            product_name = "muffin",
//            ingredient = "salt",
//            quantity = 2.0,
//            amount = 2000
//        )
//
//        useCases.insert(listOf(productIngredient1,productIngredient2, productIngredient3))
//
//        val allProductIngredients = useCases.getIngredientsForProduct("bread").getOrAwaitValue()
//        assertThat(allProductIngredients).containsExactly(productIngredient1, productIngredient2)
//    }
//
//}