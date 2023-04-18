package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.components.AddAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.device.components.DeviceRow

@Composable
fun DeviceScreen(
    deviceViewModel: DeviceViewModel = hiltViewModel(),
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    val state = deviceViewModel.state.value
    Column(modifier = Modifier.fillMaxSize()) {
        AssemblyInfo()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.devices) { device ->
                DeviceRow(device = device) {
                    assemblyViewModel.selectedDevice = it
                    assemblyViewModel.isShowDialog = true
                }
            }
        }
    }
    if (assemblyViewModel.isShowDialog) {
        AddAssemblyDialog()
    }
}