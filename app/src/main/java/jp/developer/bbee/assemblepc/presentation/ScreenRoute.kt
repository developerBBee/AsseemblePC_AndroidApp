package jp.developer.bbee.assemblepc.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import jp.developer.bbee.assemblepc.R

sealed class ScreenRoute(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    data object TopScreen : ScreenRoute("top_screen", Icons.Default.Home, R.string.top_screen)
    data object SelectionScreen : ScreenRoute("selection_screen", Icons.Default.Category, R.string.selection_screen)
    data object DeviceScreen : ScreenRoute("device_screen", Icons.AutoMirrored.Filled.ManageSearch, R.string.device_screen)
    data object AssemblyScreen : ScreenRoute("assembly_screen", Icons.Default.Build, R.string.assembly_screen)
}
