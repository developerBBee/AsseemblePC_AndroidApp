package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DeviceScreen(
    viewModel: DeviceViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.devices) { device ->
            Text(text = device.name)
        }
    }
}