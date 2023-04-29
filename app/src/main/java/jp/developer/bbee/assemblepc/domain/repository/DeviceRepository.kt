package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.data.remote.DeviceDto
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate

interface DeviceRepository {
    suspend fun getDeviceList(device: String): DeviceDto

    suspend fun loadAssembly(assemblyId: Int): List<Assembly>
    suspend fun loadAllAssembly(): List<Assembly>
    suspend fun insertAssembly(assembly: Assembly)
    suspend fun loadMaxAssemblyId(): Int?
    suspend fun deleteAssembly(assembly: Assembly)

    suspend fun existDeviceUpdate(device: String): Int
    suspend fun loadDeviceUpdate(device: String): List<DeviceUpdate>
    suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate)

    suspend fun loadDevice(device: String): List<Device>
    suspend fun insertDevice(device: Device)
}