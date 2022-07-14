package me.asiimwedismas.kmega_mono.module_bakery.presentation.report

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.*
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.components.AppBar
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.BakeryReportScreen.*
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.components.BakeryReportTabRow
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.dispatches.DispatchedBreakDownScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.dispatches.DispatchesBody
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.factory.FactoryBody
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.moneys.MoneysScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.outlets.OutletsBody
import me.asiimwedismas.kmega_mono.ui.common_components.DatePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BakeryReport(
    onNavigationIconClick: () -> Unit,
    viewModel: BakeryReportViewModel = hiltViewModel(),
) {
    val allScreens = values().toList()
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = BakeryReportScreen.fromRoute(backStackEntry.value?.destination?.route)
    val selectedDate by viewModel.sd.selectedDate.observeAsState()
    val showCalendar by viewModel.showCalendar
    val isMakingReport by viewModel.makingReport
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val topAppBarScrollState = rememberTopAppBarScrollState()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.pinnedScrollBehavior(topAppBarScrollState)
    }

    val factoryReportCard by viewModel.factoryReportCard
    val salesmenReport by viewModel.dispatchesReportCard
    val outletsReport by viewModel.outletsReportCard
    val unaccountedForDispatches by viewModel.dispatchedBreakDownReportCard
    val profitsReportCard by viewModel.profitsReportCard

    Scaffold(
        topBar = {
            AppBar(
                title = "$selectedDate",
                onNavigationIconClick = onNavigationIconClick,
                onPreviousDateClick = viewModel::selectPreviousDate,
                onSelectDateClick = viewModel::toggleShowCalendar,
                onNextDateClick = viewModel::selectNextDate,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BakeryReportTabRow(allScreens = allScreens,
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                },
                currentScreen = currentScreen)
        }
    ) { innerPadding ->
        if (showCalendar) {
            DatePickerDialog(
                currentSelected = viewModel.sd.instance.value!!.timeInMillis,
                onDateSelected = viewModel::changeDate,
                onDismiss = viewModel::toggleShowCalendar
            )
        }

        val swipeRefreshState = rememberSwipeRefreshState(isMakingReport)
        SwipeRefresh(
            modifier = Modifier.padding(innerPadding),
            state = swipeRefreshState,
            onRefresh = viewModel::makeReport
        ) {
            BakeryReportNavHost(
                navController,
                factoryReportCard = factoryReportCard,
                dispatchesReport = salesmenReport,
                outletsReport = outletsReport,
                dispatchedBreakDownReport = unaccountedForDispatches,
                profitsReportCard = profitsReportCard
            )
        }
    }
}

@Composable
fun BakeryReportNavHost(
    navController: NavHostController,
    factoryReportCard: FactoryReportCard,
    dispatchesReport: DispatchesReportCard,
    outletsReport: OutletReportCard,
    dispatchedBreakDownReport: DispatchedBreakDown,
    profitsReportCard: ProfitsReportCard,
) {
    NavHost(
        navController = navController,
        startDestination = Analysis.name,
    ) {
        composable(Factory.name) {
            FactoryBody(factoryReportCard)
        }
        composable(Dispatched.name) {
            DispatchesBody(dispatchesReport)
        }
        composable(Outlets.name) {
            OutletsBody(outletsReport)
        }
        composable(Moneys.name){
            MoneysScreen(profitsReportCard)
        }
        composable(Analysis.name) {
            DispatchedBreakDownScreen(dispatchedBreakDownReport)
        }
    }
}
