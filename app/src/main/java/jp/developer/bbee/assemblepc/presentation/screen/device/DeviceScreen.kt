package jp.developer.bbee.assemblepc.presentation.screen.device

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.navigateSingle
import jp.developer.bbee.assemblepc.presentation.screen.device.components.AddAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.screen.device.components.DeviceRow
import jp.developer.bbee.assemblepc.presentation.screen.device.components.DeviceSearchText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeviceScreen(
    deviceType: DeviceType,
    navController: NavController,
    scope: CoroutineScope,
    deviceViewModel: DeviceViewModel = hiltViewModel(),
) {

    LaunchedEffect(deviceType) {
        // TODO
        //  現状はNavigationパラメータでdeviceTypeを受け取っているが、
        //  選択したdeviceTypeをリポジトリに保存して、Flowで取得するように変更する方がUIがシンプルになる
        deviceViewModel.getDeviceList(deviceType)
    }

    DisposableEffect(Unit) {
        val job = scope.launch {
            deviceViewModel.navigationSideEffect.collect {
                navController.navigateSingle(ScreenRoute.AssemblyScreen)
            }
        }

        onDispose { job.cancel() }
    }
    val uiState by deviceViewModel.uiState.collectAsStateWithLifecycle()
    val dialogUiState by deviceViewModel.dialogUiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            DeviceUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is DeviceUiState.Error -> {
                val text = stringResource(id = R.string.network_err_msg)
                val errorText = if (state.error.isNullOrEmpty()) "" else "\n\n${state.error}"
                Text(
                    text = "$text$errorText",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }

            is DeviceUiState.Success -> {
                DeviceSearchText(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    currentSearchText = state.searchText,
                    onSearchChanged = { deviceViewModel.searchDevice(it) },
                    currentSortType = state.currentDeviceSort,
                    onSortChanged = { deviceViewModel.changeSortType(it) },
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(state.devices) { device ->
                        DeviceRow(device = device) {
                            deviceViewModel.notifyDeviceSelected(it)
                        }
                    }
                }
            }
        }
    }

    dialogUiState?.device?.let { selectedDevice ->
        AddAssemblyDialog(
            device = selectedDevice,
            onDismiss = { deviceViewModel.clearDialog() },
            onAddAssembly = { device -> deviceViewModel.addAssembly(device) },
        )
    }
}