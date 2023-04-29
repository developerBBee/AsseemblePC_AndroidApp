package jp.developer.bbee.assemblepc.presentation.components

import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
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
                // TopScreenの場合は他のアイコン色を黒にする
                unselectedContentColor = if (currentRoute?.contains(TopScreen.route) == true)
                    Color.Black else LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                onClick = {
                    currentRoute?.let { currentRoute ->
                        // 現在の画面のルートと異なる場合のみ遷移する
                        if (!currentRoute.contains(screenRoute.route)) {
                            val arguments = backStackEntry?.arguments
                            onClickNavigate(navController, screenRoute, currentRoute, arguments)
                        }
                    }
                }
            )
        }
    }
}

fun onClickNavigate(
    navController: NavController,
    screenRoute: ScreenRoute,
    currentRoute: String,
    arguments: Bundle?
) {
    // TODO: 暫定対応　画面再描画になってしまうので検討が必要
    if (currentRoute.contains(TopScreen.route)) {
        navController.navigate(TopScreen.route + "/show")
        return
    }

    val id = arguments?.getString("id")
    val name = arguments?.getString("name")
    val device = arguments?.getString("device")
    if (screenRoute == TopScreen) {
        navController.navigate(screenRoute.route)
    } else {
        navController.navigate(screenRoute.route + "/$id" + "/$name" + "/$device")
    }
}