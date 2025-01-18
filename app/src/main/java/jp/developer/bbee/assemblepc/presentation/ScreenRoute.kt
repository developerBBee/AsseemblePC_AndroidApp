package jp.developer.bbee.assemblepc.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.navOptions
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute(@StringRes val resourceId: Int) {
    @Serializable
    data object TopScreen : ScreenRoute(R.string.top_screen)

    @Serializable
    data object SelectionScreen : ScreenRoute(R.string.selection_screen)

    @Serializable
    data class DeviceScreen(
        val deviceType: DeviceType = DeviceType.PC_CASE
    ) : ScreenRoute(R.string.device_screen)

    @Serializable
    data object AssemblyScreen : ScreenRoute(R.string.assembly_screen)

    fun getIcon(): ImageVector = when (this) {
        is TopScreen -> Icons.Default.Home
        is SelectionScreen -> Icons.Default.Category
        is DeviceScreen -> Icons.AutoMirrored.Filled.ManageSearch
        is AssemblyScreen -> Icons.Default.Build
    }
}

val ROUTE_LIST = listOf(
    TopScreen,
    SelectionScreen,
    DeviceScreen(),
    AssemblyScreen
)

fun NavBackStackEntry.toScreenRoute(): ScreenRoute? {
    // Lint check incorrectly in K2 mode https://issuetracker.google.com/issues/372175033
    return ROUTE_LIST.firstOrNull { destination.hasRoute(it::class) }
}

fun NavController.navigateSingle(screenRoute: ScreenRoute) {
    popBackStack(screenRoute, true)
    if (screenRoute is DeviceScreen) {
        DeviceType.entries.forEach {
            popBackStack(DeviceScreen(it), true)
        }
    }
    val options = navOptions {
        launchSingleTop = true
    }
    navigate(screenRoute, options)
}
