package me.asiimwedismas.kmega_mono


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import me.asiimwedismas.kmega_mono.module_bakery.presentation.BakeryDashboard
import me.asiimwedismas.kmega_mono.ui.theme.Kmega_monoTheme

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            Kmega_monoTheme {
                BakeryDashboard()
            }
        }
    }
}
