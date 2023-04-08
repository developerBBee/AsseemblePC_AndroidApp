package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.common.NetworkResponse
import jp.developer.bbee.assemblepc.domain.use_case.GetDeviceUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val deviceUseCase: GetDeviceUseCase,
    // navigate()のrouteパラメータを受け取るためのSavedStateHandle
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(DeviceState())
    val state: State<DeviceState> = _state

    init {
        savedStateHandle.get<String>("device")?.let {
            getDeviceList(it)
        }
    }

    private fun getDeviceList(device: String) {
        // DeviceUseCaseのflow中のemitでNetworkResponseの状態が変更される度にonEachが呼ばれる
        deviceUseCase(device).onEach {
            when (it) {
                is NetworkResponse.Loading -> {
                    _state.value = DeviceState(isLoading = true)
                }
                is NetworkResponse.Success -> {
                    _state.value = DeviceState(devices = it.data ?: emptyList())
                }
                is NetworkResponse.Failure -> {
                    _state.value = DeviceState(error = it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}