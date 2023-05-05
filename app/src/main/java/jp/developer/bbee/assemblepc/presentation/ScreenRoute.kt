package jp.developer.bbee.assemblepc.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.ui.graphics.vector.ImageVector
import jp.developer.bbee.assemblepc.R

sealed class ScreenRoute(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object TopScreen : ScreenRoute("top_screen", Icons.Default.Home, R.string.top_screen)
    object SelectionScreen : ScreenRoute("selection_screen", Icons.Default.Category, R.string.selection_screen)
    object DeviceScreen : ScreenRoute("device_screen", Icons.Default.ManageSearch, R.string.device_screen)
    object AssemblyScreen : ScreenRoute("assembly_screen", Icons.Default.Build, R.string.assembly_screen)
}
