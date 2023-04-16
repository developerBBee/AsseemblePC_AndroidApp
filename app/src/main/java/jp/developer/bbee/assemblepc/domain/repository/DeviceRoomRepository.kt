package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import kotlinx.coroutines.flow.Flow

interface DeviceRoomRepository {

    suspend fun existDeviceUpdate(device: String): Int
    suspend fun loadDeviceUpdate(device: String): List<DeviceUpdate>
    suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate)

    fun loadDevice(device: String): Flow<List<Device>>
    suspend fun insertDevice(device: Device)
}