package jp.developer.bbee.assemblepc.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen

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
                selected = currentRoute?.contains(screenRoute.route) == true,
                onClick = {
                    if (currentRoute?.contains(screenRoute.route) == true) return@BottomNavigationItem
                    // TODO: 暫定対応　画面再描画になってしまうので検討が必要
                    if (currentRoute?.contains(TopScreen.route) == true) {
                        navController.navigate(TopScreen.route + "/show")
                        return@BottomNavigationItem
                    }

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