package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory

import android.util.Log
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.FactoryScreens.*
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.damages.DamagesScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.damages.DamagesViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.ProductionScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.ProductionViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AppBar
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.MeduimAppBar
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.store_balance.StoreBalanceScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.store_balance.StoreBalanceViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.components.FactoryScreensTabRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Factory(
    onNavigationIconClick: () -> Unit,
    factoryViewModel: FactoryViewModel = hiltViewModel(),
    productionViewModel: ProductionViewModel = hiltViewModel(),
    storeBalanceViewModel: StoreBalanceViewModel = hiltViewModel(),
    damagesViewModel: DamagesViewModel = hiltViewModel(),
) {
    val allScreens = FactoryScreens.values().toList()
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = FactoryScreens.fromRoute(backStackEntry.value?.destination?.route)

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    val selectedDate by factoryViewModel.dates.selectedDate.observeAsState("")
    val showCalendar by factoryViewModel.showCalendar

    LaunchedEffect(key1 = true) {
        Log.e("LAUNCHED", "")
        factoryViewModel.damagesViewModel = damagesViewModel
        factoryViewModel.productionViewModel = productionViewModel
        factoryViewModel.storeBalanceViewModel = storeBalanceViewModel
    }

    Scaffold(
        topBar = {
            AppBar(
                title = selectedDate,
                onNavigationIconClick = onNavigationIconClick,
                onPreviousDateClick = factoryViewModel::selectPreviousDate,
                onSelectDateClick = factoryViewModel::toggleShowCalendar,
                onNextDateClick = factoryViewModel::selectNextDate,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            FactoryScreensTabRow(allScreens = allScreens,
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                },
                currentScreen = currentScreen)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Production.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Production.name) {
                ProductionScreen(productionViewModel)
            }
            composable(Damages.name) {
                DamagesScreen(damagesViewModel)
            }
            composable(Audit.name) {
                StoreBalanceScreen(storeBalanceViewModel)
            }
        }
    }
}
