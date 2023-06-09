package jp.developer.bbee.assemblepc.presentation.components

import android.os.Bundle
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
import jp.developer.bbee.assemblepc.presentation.ui.theme.Purple700

@Composable
fun BottomNavBar(
    navController: NavController,
    items: List<ScreenRoute>
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    BottomNavigation {
        items.forEach { screenRoute ->
            val arguments = backStackEntry?.arguments
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
                unselectedContentColor = if (
                    currentRoute?.contains(TopScreen.route) == true
                    && arguments?.getString("id") == null
                )
                    Purple700 else LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                onClick = {
                    currentRoute?.let { currentRoute ->
                        // 現在の画面のルートと異なる場合のみ遷移する
                        if (!currentRoute.contains(screenRoute.route)) {
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
    val id = arguments?.getString("id")
    val name = arguments?.getString("name")
    val device = arguments?.getString("device")
    if (currentRoute.contains(TopScreen.route) && id == null) {
        // TODO: 暫定対応　画面再描画になってしまうので検討が必要
        navController.navigate(TopScreen.route + "/show")
    } else {
        navController.navigate(screenRoute.route + "/$id" + "/$name" + "/$device")
    }
}