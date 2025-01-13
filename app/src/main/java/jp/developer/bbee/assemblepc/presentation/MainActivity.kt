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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
import jp.developer.bbee.assemblepc.presentation.components.BottomNavBar
import jp.developer.bbee.assemblepc.presentation.components.HeaderInfoBar
import jp.developer.bbee.assemblepc.presentation.screen.assembly.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.screen.device.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.screen.selection.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.screen.top.TopScreen
import jp.developer.bbee.assemblepc.presentation.ui.theme.AssemblePCTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark = isSystemInDarkTheme()
            LaunchedEffect(isDark) {
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
            }

            AssemblePCApp(modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()))
        }
    }
}

@Composable
private fun AssemblePCApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel()
) {
    AssemblePCTheme {
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val composition = (uiState as? AppUiState.Selected)?.composition

        Scaffold(
            modifier = modifier,
            topBar = {
                HeaderInfoBar(composition = composition)
            },
            bottomBar = {
                BottomNavBar(
                    navController = navController,
                    composition = composition,
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
                    startDestination = TopScreen,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable<TopScreen> {
                        TopScreen(
                            navController = navController,
                            scope = scope
                        )
                    }

                    composable<SelectionScreen> {
                        SelectionScreen(navController = navController)
                    }

                    composable<DeviceScreen> { entry ->
                        val deviceType = entry.toRoute<DeviceScreen>().deviceType
                        DeviceScreen(
                            deviceType = deviceType,
                            navController = navController,
                            scope = scope
                        )
                    }

                    composable<AssemblyScreen> {
                        AssemblyScreen()
                    }
                }
            }
        }
    }
}
