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
                onClick = { navController.navigate(screenRoute.route) }
            )
        }
    }
}