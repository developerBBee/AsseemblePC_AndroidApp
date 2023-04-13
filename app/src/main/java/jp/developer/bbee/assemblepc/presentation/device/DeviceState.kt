package jp.developer.bbee.assemblepc.presentation.device

import jp.developer.bbee.assemblepc.domain.model.Device

data class DeviceState(
    val isLoading: Boolean = false,
    val devices: List<Device> = emptyList(),
    val error: String? = null
)
