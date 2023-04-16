package jp.developer.bbee.assemblepc.data.repository

import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.domain.repository.DeviceRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeviceRoomRepositoryImpl @Inject constructor(
    val assemblyDeviceDao: AssemblyDeviceDao
) : DeviceRoomRepository {
    override suspend fun existDeviceUpdate(device: String): Int {
        return assemblyDeviceDao.existDeviceUpdate(device)
    }

    override suspend fun loadDeviceUpdate(device: String): List<DeviceUpdate> {
        return assemblyDeviceDao.loadDeviceUpdate(device)
    }

    override suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate) {
        assemblyDeviceDao.insertDeviceUpdate(deviceUpdate)
    }

    override fun loadDevice(device: String): Flow<List<Device>> {
        return assemblyDeviceDao.loadDevice(device)
    }

    override suspend fun insertDevice(device: Device) {
        assemblyDeviceDao.insertDevice(device)
    }
}