package me.asiimwedismas.kmega_mono

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import me.asiimwedismas.kmega_mono.module_bakery.jobs.BakeryDBsync
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.ProductionScreen


import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.IngredientScreen
import me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient.IngredientViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.BakeryReport
import me.asiimwedismas.kmega_mono.ui.theme.Kmega_monoTheme

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        syncBakeryDB()

        setContent {
            Kmega_monoTheme {
                ProductionScreen()
            }
        }
    }

    private fun syncBakeryDB() {
        val workManager = WorkManager.getInstance(applicationContext)

        val workRequest = BakeryDBsync.buildWorkRequest()
        workManager.enqueueUniqueWork(BakeryDBsync.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest)
    }
}
