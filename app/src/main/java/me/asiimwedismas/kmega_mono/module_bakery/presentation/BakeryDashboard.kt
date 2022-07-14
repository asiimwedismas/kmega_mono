package me.asiimwedismas.kmega_mono.module_bakery.presentation

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.asiimwedismas.kmega_mono.module_bakery.presentation.BakeryScreen.*
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.Factory
import me.asiimwedismas.kmega_mono.module_bakery.presentation.finance.FinanceScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.IngredientScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.BakeryReport
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.components.BakeryScreensTabRow
import me.asiimwedismas.kmega_mono.module_bakery.presentation.salesman.PeriodDeficitsReport


@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun BakeryDashboard() {
    val allScreens = values().toList()
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = BakeryScreen.fromRoute(backStackEntry.value?.destination?.route)

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val topAppBarScrollState = rememberTopAppBarScrollState()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec, topAppBarScrollState)
    }

    NavHost(
        navController = navController,
        startDestination = "home",
    ) {

        composable("home") {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    MediumTopAppBar(
                        title = {
                            Text(text = "Bakery")
                        },
                        scrollBehavior = scrollBehavior
                    )
                }
            ) { innerPadding ->
                Surface(Modifier.padding(innerPadding)) {
                    BakeryScreensTabRow(allScreens = allScreens,
                        onTabSelected = { screen ->
                            navController.navigate(screen.name)
                        },
                        currentScreen = currentScreen
                    )
                }
            }
        }
        composable(Ingredients.name) {
            IngredientScreen(
                onNavigationIconClick = navController::navigateUp
            )
        }
        composable(Factory.name) {
            Factory(
                onNavigationIconClick = navController::navigateUp
            )
        }
        composable(Report.name) {
            BakeryReport(
                onNavigationIconClick = navController::navigateUp
            )
        }
        composable(Expenditures.name) {
            FinanceScreen(
                onNavigationIconClick = navController::navigateUp
            )
        }
        composable(SalesmenDeficitRange.name) {
            PeriodDeficitsReport(
                onNavigationIconClick = navController::navigateUp
            )
        }
    }


}
