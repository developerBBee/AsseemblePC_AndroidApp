package jp.developer.bbee.assemblepc.presentation.device

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.use_case.AddAssemblyUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssemblyViewModel @Inject constructor(
    private val addAssemblyUseCase: AddAssemblyUseCase,
) : ViewModel() {
    private val TAG = "AssemblyViewModel"

    private val _state = mutableStateOf(AssemblyState())
    val state: State<AssemblyState> = _state

    var isShowDialog by mutableStateOf(false)

    var selectAssemblyId = 0
    var selectedDevice: Device? = null

    fun addAssembly() {
        selectedDevice?.let {
            val assembly = Assembly(
                assemblyId = selectAssemblyId,
                assemblyName = "TEST",
                deviceId = it.id,
                deviceType = it.device,
                deviceName = it.name,
                deviceImgUrl = it.imgurl,
                devicePriceSaved = it.price,
                devicePriceRecent = it.price
            )
            viewModelScope.launch {
                addAssemblyUseCase(assembly)
            }
        }
    }
}