package jp.developer.bbee.assemblepc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.bbee.assemblepc.presentation.device.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.ui.theme.AssemblePCTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssemblePCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.TopScreen.route,
                        ) {
                        composable(ScreenRoute.TopScreen.route) {
                            Button(
                                onClick = {
                                    navController.navigate(ScreenRoute.DeviceScreen.route + "/cpu")
                                }
                            ) {
                                Text(text = "PUSH")
                            }
                        }
                        composable(ScreenRoute.DeviceScreen.route + "/{device}") {
                            DeviceScreen()
                        }
                    }
                }
            }
        }
    }
}
