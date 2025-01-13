package jp.developer.bbee.assemblepc.data.repository

import jp.developer.bbee.assemblepc.data.remote.DeviceApi
import jp.developer.bbee.assemblepc.data.remote.toIntUpdate
import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val api: DeviceApi,
    private val assemblyDeviceDao: AssemblyDeviceDao
) : DeviceRepository {

    private var apiUpdate: Int = 0

    private val storedUpdate: MutableMap<DeviceType, Int> = mutableMapOf(
        DeviceType.PC_CASE to 0,
        DeviceType.MOTHER_BOARD to 0,
        DeviceType.POWER_SUPPLY to 0,
        DeviceType.CPU to 0,
        DeviceType.CPU_COOLER to 0,
        DeviceType.MEMORY to 0,
        DeviceType.SSD to 0,
        DeviceType.HDD to 0,
        DeviceType.VIDEO_CARD to 0,
        DeviceType.OS to 0,
        DeviceType.DISPLAY to 0,
        DeviceType.KEYBOARD to 0,
        DeviceType.MOUSE to 0,
        DeviceType.DVD_DRIVE to 0,
        DeviceType.BD_DRIVE to 0,
        DeviceType.SOUND_CARD to 0,
        DeviceType.SPEAKER to 0,
        DeviceType.FAN_CONTROLLER to 0,
        DeviceType.CASE_FAN to 0
    )

    override suspend fun getDeviceList(deviceType: DeviceType): List<Device> {
        if (apiUpdate == 0) {
            apiUpdate = api.getLastUpdate().toIntUpdate()
        }
        if (storedUpdate.getOrDefault(deviceType, 0) == 0) {
            val storedDeviceUpdates = assemblyDeviceDao.loadDeviceUpdate(deviceType.key)
            if (storedDeviceUpdates.isNotEmpty()) {
                storedUpdate.put(deviceType, storedDeviceUpdates.first().update)
            }
        }
        if (storedUpdate.getOrDefault(deviceType, 0) < apiUpdate) {
            val deviceList = api.getDeviceList(deviceType.key).toDevice()
            deviceList.forEach { assemblyDeviceDao.insertDevice(it) }
            assemblyDeviceDao.insertDeviceUpdate(DeviceUpdate(deviceType.key, apiUpdate))
            return deviceList
        }
        return assemblyDeviceDao.loadDevice(deviceType.key)
    }

    override suspend fun loadAssembly(assemblyId: Int): List<Assembly> {
        return assemblyDeviceDao.loadAssembly(assemblyId)
    }

    override suspend fun loadAllAssembly(): List<Assembly> {
        return assemblyDeviceDao.loadAllAssembly()
    }

    override suspend fun insertAssembly(assembly: Assembly) {
        assemblyDeviceDao.insertAssembly(assembly)
    }

    override suspend fun loadMaxAssemblyId(): Int? {
        return assemblyDeviceDao.loadMaxAssemblyId()
    }

    override suspend fun deleteAssembly(assembly: Assembly) {
        assemblyDeviceDao.deleteAssembly(assembly)
    }

    override suspend fun deleteAssemblyById(assemblyId: Int) {
        assemblyDeviceDao.deleteAssemblyById(assemblyId)
    }

    override suspend fun renameAssemblyById(assemblyName: String, assemblyId: Int) {
        assemblyDeviceDao.renameAssemblyById(assemblyName, assemblyId)
    }

    override suspend fun existDeviceUpdate(device: String): Int {
        return assemblyDeviceDao.existDeviceUpdate(device)
    }

    override suspend fun loadDeviceUpdate(device: String): List<DeviceUpdate> {
        return assemblyDeviceDao.loadDeviceUpdate(device)
    }

    override suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate) {
        assemblyDeviceDao.insertDeviceUpdate(deviceUpdate)
    }

    override suspend fun loadDevice(device: String): List<Device> {
        return assemblyDeviceDao.loadDevice(device)
    }

    override suspend fun insertDevice(device: Device) {
        assemblyDeviceDao.insertDevice(device)
    }
}