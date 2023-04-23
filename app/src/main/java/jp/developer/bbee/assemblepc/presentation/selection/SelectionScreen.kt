package jp.developer.bbee.assemblepc.presentation.selection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.selection.components.ButtonsRow

val deviceTypes: List<Map<String, String>> = mutableListOf(
    mapOf("text" to "PCケース", "path" to "pccase"),
    mapOf("text" to "マザーボード", "path" to "motherboard"),
    mapOf("text" to "電源", "path" to "powersupply"),
    mapOf("text" to "CPU", "path" to "cpu"),
    mapOf("text" to "CPUクーラー", "path" to "cpucooler"),
    mapOf("text" to "メモリ", "path" to "pcmemory"),
    mapOf("text" to "SSD", "path" to "ssd"),
    mapOf("text" to "HDD", "path" to "hdd35inch"),
    mapOf("text" to "グラフィック\nボード", "path" to "videocard"),
)

@Composable
fun SelectionScreen(
    navController: NavController,
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AssemblyInfo()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            for (i in deviceTypes.indices step 2) {
                val leftType = deviceTypes.get(i)
                val rightType = if (deviceTypes.size > i + 1) deviceTypes.get(i + 1) else null
                ButtonsRow(
                    leftContentText = leftType.getOrDefault("text", ""),
                    rightContentText = rightType?.getOrDefault("text", ""),
                    onClickLeftButton = {
                        navController.navigate(
                            DeviceScreen.route
                                    + "/${assemblyViewModel.selectedAssemblyId}"
                                    + "/${assemblyViewModel.selectedAssemblyName.value}"
                                    + "/${leftType.getOrDefault("path", "")}"
                        )
                    },
                    onClickRightButton = {
                        navController.navigate(
                            DeviceScreen.route
                                    + "/${assemblyViewModel.selectedAssemblyId}"
                                    + "/${assemblyViewModel.selectedAssemblyName.value}"
                                    + "/${rightType?.getOrDefault("path", "")}"
                        )
                    }
                )
            }
        }
    }
}