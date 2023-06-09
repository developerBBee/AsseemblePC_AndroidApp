package jp.developer.bbee.assemblepc.presentation.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.selection.components.VariableButtonsRow

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
    mapOf("text" to "OS", "path" to "ossoft"),
    mapOf("text" to "ディスプレイ", "path" to "lcdmonitor"),
    mapOf("text" to "キーボード", "path" to "keyboard"),
    mapOf("text" to "マウス", "path" to "mouse"),
    mapOf("text" to "DVDドライブ", "path" to "dvddrive"),
    mapOf("text" to "BDドライブ", "path" to "bluraydrive"),
    mapOf("text" to "サウンドカード", "path" to "soundcard"),
    mapOf("text" to "スピーカー", "path" to "pcspeaker"),
    mapOf("text" to "ファン\nコントローラー", "path" to "fancontroller"),
    mapOf("text" to "ファン", "path" to "casefan"),
)

@Composable
fun SelectionScreen(
    navController: NavController,
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AssemblyInfo(
            assemblyName = assemblyViewModel.selectedAssemblyName.value,
            prices = assemblyViewModel.assemblies.value.sumOf { it.devicePriceRecent },
            isNoPrice = assemblyViewModel.assemblies.value.any { it.devicePriceRecent == 0 }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            VariableButtonsRow(
                navController = navController,
                assemblyViewModel = assemblyViewModel
            )
        }
    }
}