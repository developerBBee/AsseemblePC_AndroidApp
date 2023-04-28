package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.use_case.AddAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetMaxAssemblyIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssemblyViewModel @Inject constructor(
    private val addAssemblyUseCase: AddAssemblyUseCase,
    private val getMaxAssemblyIdUseCase: GetMaxAssemblyIdUseCase,
    private val getAssemblyUseCase: GetAssemblyUseCase,
    // navigate()のrouteパラメータを受け取るためのSavedStateHandle
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val TAG = "AssemblyViewModel"

    private val _state = mutableStateOf(AssemblyState())
    val state: State<AssemblyState> = _state

    var isShowDialog by mutableStateOf(false)

    var selectedAssemblyId = 0
    var selectedAssemblyName = mutableStateOf("")
    var selectedDevice: Device? = null

    val assemblies = mutableStateOf(listOf<Assembly>())

    init {
        val id = savedStateHandle.get<String>("id")?.toInt()
        if (id != 0 && id != null) {
            selectedAssemblyId = id
            viewModelScope.launch {
                val assemblyList = getAssemblyUseCase(id)
                if (assemblyList.isNotEmpty()) {
                    selectedAssemblyName.value = assemblyList[0].assemblyName
                    assemblies.value = assemblyList
                } else {
                    savedStateHandle.get<String>("name")?.let {
                        selectedAssemblyName.value = it
                    }
                }
            }
        } else {
            savedStateHandle.get<String>("name")?.let {
                selectedAssemblyName.value = it
            }
            viewModelScope.launch {
                val maxId = getMaxAssemblyIdUseCase()
                selectedAssemblyId = (maxId ?: 0) + 1
            }
        }
    }

    fun addAssembly() {
        selectedDevice?.let {
            val assembly = Assembly(
                assemblyId = selectedAssemblyId,
                assemblyName = selectedAssemblyName.value,
                deviceId = it.id,
                deviceType = it.device,
                deviceName = it.name,
                deviceImgUrl = it.imgurl,
                deviceDetail = it.detail,
                devicePriceSaved = it.price,
                devicePriceRecent = it.price
            )
            viewModelScope.launch {
                addAssemblyUseCase(assembly)
            }
        }
    }
}