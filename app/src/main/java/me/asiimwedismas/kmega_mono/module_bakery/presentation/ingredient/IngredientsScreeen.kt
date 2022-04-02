package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components.AddIngredientFAB
import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components.IngredientList
import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.components.IngredientAppBar

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun IngredientScreen(
    onNavigationIconClick: () -> Unit,
    viewModel: IngredientViewModel = hiltViewModel(),
) {

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    val ingredientList by viewModel.ingredientList.observeAsState(initial = listOf())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            IngredientAppBar(
                scrollBehavior = scrollBehavior,
                navAction = onNavigationIconClick,
                onRefresh = viewModel::onSyncDB
            )
        },
        floatingActionButton = {
            AddIngredientFAB {
            }
        },
        content = {
            IngredientList(ingredientList)
        }
    )
}