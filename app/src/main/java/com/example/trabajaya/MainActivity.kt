package com.example.trabajaya

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabajaya.ui.SplashScreen.MainSplashScreen
import com.example.trabajaya.ui.theme.TrabajayaTheme
import com.example.trabajaya.ui.Screens.ConfigScreen
import com.example.trabajaya.ui.Screens.P1Screen
import com.example.trabajaya.ui.Screens.P2Screen
import com.example.trabajaya.ui.Screens.P3Screen
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import com.example.trabajaya.utils.navegation.DrawerMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrabajayaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenDirectionModule.Splash.route
                    ) {
                        composable(ScreenDirectionModule.Splash.route) {
                            MainSplashScreen(navController)
                        }
                        composable(ScreenDirectionModule.PMain.route) {
                            DrawerMenu(navController)
                        }
                        composable(ScreenDirectionModule.P1.route) {
                            P1Screen(navController)
                        }
                        composable(ScreenDirectionModule.P2.route) {
                            P2Screen(navController)
                        }
                        composable(ScreenDirectionModule.P3.route) {
                            P3Screen(navController)
                        }
                        composable(ScreenDirectionModule.Pconfig.route) {
                            ConfigScreen(navController)
                        }
                    }
                }
            }
        }
    }
}