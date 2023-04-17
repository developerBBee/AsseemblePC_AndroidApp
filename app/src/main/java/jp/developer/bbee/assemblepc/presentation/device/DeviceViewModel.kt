package jp.developer.bbee.assemblepc.presentation.device

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.common.NetworkResponse
import jp.developer.bbee.assemblepc.domain.model.Device
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
    private val TAG = "DeviceViewModel"

    var isShowDialog by mutableStateOf(false)

    private val _state = mutableStateOf(DeviceState())
    val state: State<DeviceState> = _state

    init {
        savedStateHandle.get<String>("device")?.let {
            Log.d(TAG, "getDeviceList(): for $it")
            getDeviceList(it)
        }
    }

    private fun getDeviceList(device: String) {
        // DeviceUseCaseのflow中のemitでNetworkResponseの状態が変更される度にonEachが呼ばれる
        deviceUseCase(device).onEach {
            when (it) {
                is NetworkResponse.Loading -> {
                    _state.value = DeviceState(isLoading = true)
                    Log.d(TAG, "NetworkResponse is Loading")
                }
                is NetworkResponse.Success -> {
                    _state.value = DeviceState(devices = it.data ?: emptyList())
                    Log.d(TAG, "NetworkResponse is Success")
                }
                is NetworkResponse.Failure -> {
                    _state.value = DeviceState(error = it.error)
                    Log.d(TAG, "NetworkResponse is error : ${it.error}")
                }
            }
        }.launchIn(viewModelScope)
    }
}