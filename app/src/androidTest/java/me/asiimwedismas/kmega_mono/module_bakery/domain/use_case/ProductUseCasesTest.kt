package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.filters.SmallTest
//import com.google.common.truth.Truth.assertThat
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
//import me.asiimwedismas.kmega_mono.getOrAwaitValue
//import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
//import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.ProductDao
//import me.asiimwedismas.kmega_mono.module_bakery.data.repository.ProductRepositoryImp
//import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository
//import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import javax.inject.Inject
//import javax.inject.Named
//
//
//@SmallTest
//@ExperimentalCoroutinesApi
//@HiltAndroidTest
//class ProductUseCasesTest {
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    @Named("test_db")
//    lateinit var database: BakeryDatabase
//
//    private lateinit var dao: ProductDao
//    private lateinit var repo: ProductRepository
//    private lateinit var useCases: ProductUseCases
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//
//        dao = database.productDao()
//        repo = ProductRepositoryImp(dao)
//
//        useCases = ProductUseCases(
//            InsertProducts(repo),
//            UpdateProducts(repo),
//            DeleteProducts(repo),
//            GetAllProducts(repo),
//            GetProduct(repo),
//        )
//    }
//
//    @After
//    fun tearDown() {
//        database.close()
//    }
//
//    @Test
//    fun insertProducts() = runBlockingTest {
//        val bread = BakeryProduct("bread")
//
//        val rockBun = BakeryProduct("rock bun")
//
//        useCases.insertProducts(listOf(bread, rockBun))
//
//        val allProducts = useCases.getAllProducts().getOrAwaitValue()
//        assertThat(allProducts).containsExactly(bread, rockBun)
//    }
//
//    @Test
//    fun ignoreDuplicateInsert() = runBlockingTest {
//        val bread = BakeryProduct("bread")
//
//        val duplicate = BakeryProduct("bread")
//
//        useCases.insertProducts(listOf(bread, duplicate))
//
//        val allProducts = useCases.getAllProducts().getOrAwaitValue()
//        assertThat(allProducts).containsExactly(bread)
//    }
//
//    @Test
//    fun updateProducts() = runBlockingTest {
//        val bread = BakeryProduct("bread")
//        val rockBun = BakeryProduct("rock bun")
//
//        useCases.insertProducts(listOf(bread, rockBun))
//
//        val updatedBread = BakeryProduct(
//            "bread",
//            3,
//        )
//
//        useCases.updateProducts(listOf(updatedBread))
//
//        val allProducts = useCases.getAllProducts().getOrAwaitValue()
//        assertThat(allProducts).containsExactly(updatedBread, rockBun)
//    }
//
//    @Test
//    fun deleteProducts() = runBlockingTest {
//        val bread = BakeryProduct("bread")
//        val rockBun = BakeryProduct("rock bun")
//        val muffin = BakeryProduct("muffin")
//
//        useCases.insertProducts(listOf(bread, rockBun, muffin))
//        useCases.deleteProducts(listOf(bread, muffin))
//
//        val allProducts = useCases.getAllProducts().getOrAwaitValue()
//        assertThat(allProducts).containsExactly(rockBun)
//    }
//
//    @Test
//    fun getAllProducts() = runBlockingTest {
//        val bread = BakeryProduct("bread")
//        val rockBun = BakeryProduct("rock bun")
//        val muffin = BakeryProduct("muffin")
//
//        useCases.insertProducts(listOf(bread, rockBun, muffin))
//
//        val allProducts = useCases.getAllProducts().getOrAwaitValue()
//        assertThat(allProducts).containsExactly(rockBun, bread, muffin)
//    }
//
//    @Test
//    fun getProduct() = runBlockingTest {
//        val bread = BakeryProduct("bread")
//        val rockBun = BakeryProduct("rock bun")
//        val muffin = BakeryProduct("muffin")
//
//        useCases.insertProducts(listOf(bread, rockBun, muffin))
//
//        val allProducts = useCases.getProduct("muffin")
//        assertThat(allProducts).isEqualTo(muffin)
//    }
//
//
//}