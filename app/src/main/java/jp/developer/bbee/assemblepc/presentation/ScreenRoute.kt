package jp.developer.bbee.assemblepc.presentation

sealed class ScreenRoute(val route: String) {
    object TopScreen : ScreenRoute("top_screen")
    object SelectionScreen : ScreenRoute("selection_screen")
    object DeviceScreen : ScreenRoute("device_screen")
    object AssemblyScreen : ScreenRoute("assembly_screen")
}
