package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.data.remote.DeviceDto

interface DeviceRepository {
    suspend fun getDeviceList(device: String): DeviceDto
}