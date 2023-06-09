package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyRow

@Composable
fun AssemblyScreen(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
){
    val assemblies = assemblySort(assemblyViewModel.assemblies.value)

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        AssemblyInfo(
            assemblyName = assemblyViewModel.selectedAssemblyName.value,
            prices = assemblyViewModel.assemblies.value.sumOf { it.devicePriceRecent },
            isNoPrice = assemblyViewModel.assemblies.value.any { it.devicePriceRecent == 0 }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(assemblies) { assembly ->
                AssemblyRow(assembly)
            }
        }
    }
}

val sortList = mapOf(
    "pccase" to 1,
    "motherboard" to 2,
    "powersupply" to 3,
    "cpu" to 4,
    "cpucooler" to 5,
    "pcmemory" to 6,
    "ssd" to 7,
    "hdd35inch" to 8,
    "videocard" to 9,
    "ossoft" to 10,
    "lcdmonitor" to 11,
    "keyboard" to 12,
    "mouse" to 13,
    "dvddrive" to 14,
    "bluraydrive" to 15,
    "soundcard" to 16,
    "pcspeaker" to 17,
    "fancontroller" to 18,
    "casefan" to 19
)
fun assemblySort(assembly: List<Assembly>): List<Assembly> {
    return assembly.sortedByDescending { -sortList.getOrDefault(it.deviceType, 0) }
}