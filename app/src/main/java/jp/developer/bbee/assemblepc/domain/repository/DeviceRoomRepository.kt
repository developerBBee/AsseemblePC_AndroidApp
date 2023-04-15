package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRoomRepository {

    fun loadDevice(device: String): Flow<List<Device>>
    suspend fun insertDevice(device: Device)
}