package jp.developer.bbee.assemblepc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
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
        enableEdgeToEdge()
        setContent {
            val isDark = isSystemInDarkTheme()
            DisposableEffect(isDark) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { false },
                    navigationBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { false },
                )
                onDispose {}
            }

            AssemblePCApp(modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()))
        }
    }
}

@Composable
fun AssemblePCApp(modifier: Modifier = Modifier) {
    AssemblePCTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = modifier,
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
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .padding(paddingValues = innerPadding),
                color = MaterialTheme.colors.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = TopScreen.route,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable(TopScreen.route) {
                        TopScreen(navController)
                    }
                    composable(TopScreen.route + "/{show}") {
                        TopScreen(navController)
                    }
                    composable(TopScreen.route + "/{id}" + "/{name}" + "/{device}") {
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
