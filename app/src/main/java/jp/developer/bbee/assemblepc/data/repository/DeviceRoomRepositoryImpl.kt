package jp.developer.bbee.assemblepc.data.repository

import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.repository.DeviceRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeviceRoomRepositoryImpl @Inject constructor(
    val assemblyDeviceDao: AssemblyDeviceDao
) : DeviceRoomRepository {
    override fun loadDevice(device: String): Flow<List<Device>> {
        return assemblyDeviceDao.loadDevice(device)
    }

    override suspend fun insertDevice(device: Device) {
        assemblyDeviceDao.insertDevice(device)
    }
}