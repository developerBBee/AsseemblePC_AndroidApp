package jp.developer.bbee.assemblepc.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.*

@Composable
fun BottomNavBar(
    navController: NavController,
    items: List<ScreenRoute>
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    BottomNavigation {
        items.forEach { screenRoute ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screenRoute.icon,
                        contentDescription = "ナビゲーションアイコン"
                    )
                },
                label = { Text(text = stringResource(id = screenRoute.resourceId)) },
                selected = currentRoute == screenRoute.route,
                onClick = {
                    if (currentRoute?.contains(screenRoute.route) == true) return@BottomNavigationItem
                    // TODO デバイスID未設定の場合、AddAssemblyDialogを呼ぶようにしたい。現状は無効としている。
                    if (currentRoute == TopScreen.route) return@BottomNavigationItem

                    val id = backStackEntry?.arguments?.getString("id")
                    val name = backStackEntry?.arguments?.getString("name")
                    val device = backStackEntry?.arguments?.getString("device")
                    if (screenRoute == TopScreen) {
                        navController.navigate(screenRoute.route)
                    } else {
                        navController.navigate(screenRoute.route + "/$id" + "/$name" + "/$device")
                    }
                }
            )
        }
    }
}