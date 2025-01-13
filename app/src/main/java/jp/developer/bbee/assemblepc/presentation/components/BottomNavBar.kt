package jp.developer.bbee.assemblepc.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.navigateSingle
import jp.developer.bbee.assemblepc.presentation.toScreenRoute

@Composable
fun BottomNavBar(
    navController: NavController,
    composition: Composition?,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: ScreenRoute? = backStackEntry?.toScreenRoute()

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        ScreenRoute.ROUTE_LIST.forEach { screenRoute ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screenRoute.getIcon(),
                        contentDescription = "ナビゲーションアイコン"
                    )
                },
                label = { Text(text = stringResource(id = screenRoute.resourceId)) },
                selected = currentRoute == screenRoute,
                // TopScreenの場合は他のアイコン色を黒にする
                unselectedContentColor = if (currentRoute == ScreenRoute.TopScreen) {
                    LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                } else {
                    LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                },
                selectedContentColor = MaterialTheme.colors.primary,
                enabled = composition != null,
                onClick = {
                    currentRoute?.let { currentRoute ->
                        // 現在の画面のルートと異なる場合のみ遷移する
                        if (currentRoute != screenRoute) {
                            navController.navigateSingle(screenRoute)
                        }
                    }
                }
            )
        }
    }
}
