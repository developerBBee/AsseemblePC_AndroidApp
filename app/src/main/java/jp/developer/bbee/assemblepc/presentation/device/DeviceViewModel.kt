package jp.developer.bbee.assemblepc.presentation.device

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.common.NetworkResponse
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.use_case.GetDeviceUseCase
import jp.developer.bbee.assemblepc.presentation.device.components.SortType
import jp.developer.bbee.assemblepc.presentation.device.components.SortType.*
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

    private val _state = mutableStateOf(DeviceState())
    val state: State<DeviceState> = _state

    var searchText = mutableStateOf("")
    private var currentDeviceType: String? = null
    private var deviceBuffer = mutableMapOf<String, List<Device>>()
    var currentDeviceSort: SortType = POPULARITY

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
                    currentDeviceType = device
                    deviceBuffer[device] = it.data ?: emptyList()
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

    fun onDeviceSortChanged(sort: SortType) {
        currentDeviceSort = sort
        filterDeviceList(searchText.value)
    }

    fun setSearchText(text: String) {
        searchText.value = text
        filterDeviceList(text)
    }

    private fun filterDeviceList(text: String) {
        val searchList = createSearchList(text)

        deviceBuffer[currentDeviceType]?.let { bufferList ->
            _state.value = DeviceState(
                devices =
                when (currentDeviceSort) {
                    POPULARITY -> bufferList.sortedBy { if(it.rank > 0) it.rank else Int.MAX_VALUE }
                    NEW_ARRIVAL -> bufferList.sortedByDescending { it.releasedate }
                    PRICE_ASC -> bufferList.sortedBy { if(it.price > 0) it.price else Int.MAX_VALUE }
                    PRICE_DESC -> bufferList.sortedByDescending { if(it.price > 0) it.price else 0 }
                }.filter { device ->
                    searchList.all { search ->
                        device.name.uppercase().contains(search.uppercase())
                                || device.detail.uppercase().contains(search.uppercase())
                    }
                }
            )
        }
    }

    private fun createSearchList(searchText: String): List<String> {
        return searchText
            .trim()
            .replace("　", " ")
            .split("\\s+".toRegex())
            .map { convertToFullWidthKatakana(it) }
    }

    private fun convertToFullWidthKatakana(input: String): String {
        val sb = StringBuilder()
        for (i in input.indices) {
            val c1 = input[i]
            if (c1 == 'ﾞ' || c1 == 'ﾟ') continue // 濁音、半濁音はskip

            if (i < input.length -1) {
                val c2 = input[i+1]
                val str = c1.toString() + c2.toString()
                if ((c2 == 'ﾞ' || c2 == 'ﾟ') && kanaMap.keys.contains(str)) {
                    sb.append(kanaHalfToFullMapping(str))
                } else {
                    sb.append(kanaHalfToFullMapping(c1.toString()))
                }
            } else {
                sb.append(kanaHalfToFullMapping(c1.toString()))
            }
        }
        return sb.toString()
    }

    private fun kanaHalfToFullMapping(input: String): String {
        return kanaMap[input] ?: input
    }

    val kanaMap = mapOf(
        "ｱ" to "ア",
        "ｲ" to "イ",
        "ｳ" to "ウ",
        "ｴ" to "エ",
        "ｵ" to "オ",
        "ｶ" to "カ",
        "ｷ" to "キ",
        "ｸ" to "ク",
        "ｹ" to "ケ",
        "ｺ" to "コ",
        "ｻ" to "サ",
        "ｼ" to "シ",
        "ｽ" to "ス",
        "ｾ" to "セ",
        "ｿ" to "ソ",
        "ﾀ" to "タ",
        "ﾁ" to "チ",
        "ﾂ" to "ツ",
        "ﾃ" to "テ",
        "ﾄ" to "ト",
        "ﾅ" to "ナ",
        "ﾆ" to "ニ",
        "ﾇ" to "ヌ",
        "ﾈ" to "ネ",
        "ﾉ" to "ノ",
        "ﾊ" to "ハ",
        "ﾋ" to "ヒ",
        "ﾌ" to "フ",
        "ﾍ" to "ヘ",
        "ﾎ" to "ホ",
        "ﾏ" to "マ",
        "ﾐ" to "ミ",
        "ﾑ" to "ム",
        "ﾒ" to "メ",
        "ﾓ" to "モ",
        "ﾔ" to "ヤ",
        "ﾕ" to "ユ",
        "ﾖ" to "ヨ",
        "ﾗ" to "ラ",
        "ﾘ" to "リ",
        "ﾙ" to "ル",
        "ﾚ" to "レ",
        "ﾛ" to "ロ",
        "ﾜ" to "ワ",
        "ｦ" to "ヲ",
        "ﾝ" to "ン",
        "ｧ" to "ァ",
        "ｨ" to "ィ",
        "ｩ" to "ゥ",
        "ｪ" to "ェ",
        "ｫ" to "ォ",
        "ｬ" to "ャ",
        "ｭ" to "ュ",
        "ｮ" to "ョ",
        "ｯ" to "ッ",
        "ｰ" to "ー",
        "｡" to "。",
        "｢" to "「",
        "｣" to "」",
        "､" to "、",
        "･" to "・",
        "ｶﾞ" to "ガ",
        "ｷﾞ" to "ギ",
        "ｸﾞ" to "グ",
        "ｹﾞ" to "ゲ",
        "ｺﾞ" to "ゴ",
        "ｻﾞ" to "ザ",
        "ｼﾞ" to "ジ",
        "ｽﾞ" to "ズ",
        "ｾﾞ" to "ゼ",
        "ｿﾞ" to "ゾ",
        "ﾀﾞ" to "ダ",
        "ﾁﾞ" to "ヂ",
        "ﾂﾞ" to "ヅ",
        "ﾃﾞ" to "デ",
        "ﾄﾞ" to "ド",
        "ﾊﾞ" to "バ",
        "ﾋﾞ" to "ビ",
        "ﾌﾞ" to "ブ",
        "ﾍﾞ" to "ベ",
        "ﾎﾞ" to "ボ",
        "ﾊﾟ" to "パ",
        "ﾋﾟ" to "ピ",
        "ﾌﾟ" to "プ",
        "ﾍﾟ" to "ペ",
        "ﾎﾟ" to "ポ",
        "ｳﾞ" to "ヴ",
        "ﾜﾞ" to "ヷ",
        "ｦﾞ" to "ヺ",
    )
}