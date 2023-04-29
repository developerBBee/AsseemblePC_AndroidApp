package jp.developer.bbee.assemblepc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.*
import jp.developer.bbee.assemblepc.presentation.components.BottomNavBar
import jp.developer.bbee.assemblepc.presentation.device.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.device.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.selection.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.top.TopScreen
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

                    Scaffold(
                        bottomBar = {
                            BottomNavBar(
                                navController = navController,
                                items = listOf(
                                    TopScreen,
                                    SelectionScreen,
                                    DeviceScreen,
                                    AssemblyScreen
                                )
                            )
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = TopScreen.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(TopScreen.route) {
                                TopScreen(navController)
                            }
                            composable(TopScreen.route + "/{show}") {
                                TopScreen(navController)
                            }
                            composable(SelectionScreen.route + "/{id}" + "/{name}" + "/{device}") {
                                SelectionScreen(navController)
                            }
                            composable(DeviceScreen.route + "/{id}" + "/{name}" + "/{device}") {
                                DeviceScreen(navController)
                            }
                            composable(AssemblyScreen.route + "/{id}" + "/{name}" + "/{device}") {
                                AssemblyScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
