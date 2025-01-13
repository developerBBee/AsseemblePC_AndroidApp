package jp.developer.bbee.assemblepc.presentation.screen.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.common.Constants.kanaHalfToFull
import jp.developer.bbee.assemblepc.common.NetworkResponse
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.MAX_PRICE
import jp.developer.bbee.assemblepc.domain.model.ZERO_PRICE
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.use_case.AddAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetDeviceUseCase
import jp.developer.bbee.assemblepc.presentation.screen.device.components.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase, // TODO 現在の構成に含まれるデバイスをリストの先頭に表示するために取得する
    private val getDeviceUseCase: GetDeviceUseCase,
    private val addAssemblyUseCase: AddAssemblyUseCase,
) : ViewModel() {

    private var viewModelState = DeviceViewModelState()

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Loading)
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private val _dialogUiState = MutableStateFlow<ShowDeviceAdditionDialog?>(null)
    val dialogUiState: StateFlow<ShowDeviceAdditionDialog?> = _dialogUiState.asStateFlow()

    private val _navigationSideEffect = MutableSharedFlow<Unit>()
    val navigationSideEffect: Flow<Unit> = _navigationSideEffect.asSharedFlow()

    init {
        getCurrentCompositionUseCase()
            .onEach { composition ->
                if (composition == null) {
                    _uiState.value = DeviceUiState.Error(error = "構成が設定されていません")
                } else {
                    viewModelState = viewModelState.copy(
                        assemblyId = composition.assemblyId,
                        assemblyName = composition.assemblyName,
                        currentDeviceIdList = composition.items.map { it.deviceId }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun getDeviceList(deviceType: DeviceType) {
        viewModelState = viewModelState.copy(deviceType = deviceType)

        getDeviceUseCase(deviceType).onEach {
            when (it) {
                is NetworkResponse.Loading -> {
                    _uiState.value = DeviceUiState.Loading
                }

                is NetworkResponse.Success -> {
                    val deviceList = it.data ?: emptyList()
                    viewModelState = viewModelState.copy(devices = deviceList)
                    _uiState.value = DeviceUiState.Success(
                        devices = deviceList,
                        currentDeviceIdList = viewModelState.currentDeviceIdList,
                        searchText = viewModelState.searchText,
                        currentDeviceSort = viewModelState.currentDeviceSort,
                    )
                }

                is NetworkResponse.Failure -> {
                    _uiState.value = DeviceUiState.Error(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changeSortType(sort: SortType) {
        viewModelState = viewModelState.copy(currentDeviceSort = sort)
        filterDeviceList()
    }

    fun searchDevice(text: String) {
        viewModelState = viewModelState.copy(searchText = text)
        filterDeviceList()
    }

    private fun filterDeviceList() {
        val successState = uiState.value as? DeviceUiState.Success ?: return

        val devices = viewModelState.devices
        val searchText = viewModelState.searchText
        val sort = viewModelState.currentDeviceSort

        val searchList = createSearchList(searchText)

        _uiState.value = successState.copy(
            devices = when (sort) {
                SortType.POPULARITY -> devices.sortedBy { if (it.rank > 0) it.rank else Int.MAX_VALUE }
                SortType.NEW_ARRIVAL -> devices.sortedByDescending { it.releasedate }
                SortType.PRICE_ASC -> devices.sortedBy { if (it.price > 0) it.price else MAX_PRICE }
                SortType.PRICE_DESC -> devices.sortedByDescending { if (it.price > 0) it.price else ZERO_PRICE }
            }.filter { device ->
                searchList.all { search ->
                    device.name.contains(other = search, ignoreCase = true)
                            || device.detail.contains(other = search, ignoreCase = true)
                }
            },
            searchText = searchText,
            currentDeviceSort = sort,
        )
    }

    // TODO StringUtil関数へ
    private fun createSearchList(searchText: String): List<String> {
        return searchText
            .trim()
            .replace("　", " ")
            .split("\\s+".toRegex())
            .map { convertToFullWidthKatakana(it) }
    }

    // TODO StringUtil関数へ
    private fun convertToFullWidthKatakana(input: String): String {
        val sb = StringBuilder()
        for (i in input.indices) {
            val c1 = input[i]
            if (c1 == 'ﾞ' || c1 == 'ﾟ') continue // 濁音、半濁音はskip

            if (i < input.length -1) {
                val c2 = input[i+1]
                val str = c1.toString() + c2.toString()
                if ((c2 == 'ﾞ' || c2 == 'ﾟ') && kanaHalfToFull.keys.contains(str)) {
                    sb.append(mapKanaHalfToFull(str))
                } else {
                    sb.append(mapKanaHalfToFull(c1.toString()))
                }
            } else {
                sb.append(mapKanaHalfToFull(c1.toString()))
            }
        }
        return sb.toString()
    }

    private fun mapKanaHalfToFull(input: String): String {
        return kanaHalfToFull[input] ?: input
    }

    fun notifyDeviceSelected(device: Device) {
        _dialogUiState.value = ShowDeviceAdditionDialog(device)
    }

    fun clearDialog() {
        _dialogUiState.value = null
    }

    fun addAssembly(device: Device) {
        clearDialog()

        val assembly = Assembly(
            assemblyId = viewModelState.assemblyId,
            assemblyName = viewModelState.assemblyName,
            deviceId = device.id,
            deviceType = device.device,
            deviceName = device.name,
            deviceImgUrl = device.imgurl,
            deviceDetail = device.detail,
            devicePriceSaved = device.price,
            devicePriceRecent = device.price,
        )

        viewModelScope.launch {
            addAssemblyUseCase(assembly)
            _navigationSideEffect.emit(Unit)
        }
    }
}

private data class DeviceViewModelState(
    val assemblyId: Int = 0,
    val assemblyName: String = "",
    val deviceType: DeviceType = DeviceType.PC_CASE,
    val devices: List<Device> = emptyList(),
    val currentDeviceIdList: List<String> = emptyList(),
    val searchText: String = "",
    val currentDeviceSort: SortType = SortType.POPULARITY,
)

sealed interface DeviceUiState {
    data object Loading : DeviceUiState

    data class Success(
        val devices: List<Device>,
        val currentDeviceIdList: List<String>,
        val searchText: String,
        val currentDeviceSort: SortType,
    ) : DeviceUiState

    data class Error(val error: String?) : DeviceUiState
}

data class ShowDeviceAdditionDialog(val device: Device)
