package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.presentation.device.components.AddAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.device.components.DeviceRow
import jp.developer.bbee.assemblepc.presentation.device.components.DeviceSearchText

@Composable
fun DeviceScreen(
    navController: NavController,
    deviceViewModel: DeviceViewModel = hiltViewModel(),
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    val state = deviceViewModel.state.value
    Column(modifier = Modifier.fillMaxSize()) {
        AssemblyInfo(
            assemblyName = assemblyViewModel.selectedAssemblyName.value,
            prices = assemblyViewModel.assemblies.value.sumOf { it.devicePriceRecent },
            isNoPrice = assemblyViewModel.assemblies.value.any { it.devicePriceRecent == 0 }
        )
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            !state.error.isNullOrBlank() -> {
                Text(
                    text = "${stringResource(id = R.string.network_err_msg)}\n\n${state.error}",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
            else -> {
                DeviceSearchText(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(state.devices) { device ->
                        DeviceRow(device = device) {
                            assemblyViewModel.selectedDevice = it
                            assemblyViewModel.isShowDialog = true
                        }
                    }
                }
            }
        }
    }
    if (assemblyViewModel.isShowDialog) {
        AddAssemblyDialog(navController)
    }
}