package jp.developer.bbee.assemblepc.data.repository

import jp.developer.bbee.assemblepc.data.remote.DeviceApi
import jp.developer.bbee.assemblepc.data.remote.DeviceDto
import jp.developer.bbee.assemblepc.data.remote.toDevice
import jp.developer.bbee.assemblepc.data.remote.toIntUpdate
import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.domain.model.toDeviceDto
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val api: DeviceApi,
    private val assemblyDeviceDao: AssemblyDeviceDao
) : DeviceRepository {
    private var apiUpdate: Int = 0
    private val storedUpdate: MutableMap<String, Int> = mutableMapOf(
        "pccase" to 0,
        "motherboard" to 0,
        "powersupply" to 0,
        "cpu" to 0,
        "cpucooler" to 0,
        "pcmemory" to 0,
        "hdd35inch" to 0,
        "ssd" to 0,
        "videocard" to 0,
        "ossoft" to 0,
        "lcdmonitor" to 0,
        "keyboard" to 0,
        "mouse" to 0,
        "dvddrive" to 0,
        "bluraydrive" to 0,
        "soundcard" to 0,
        "pcspeaker" to 0,
        "fancontroller" to 0,
        "casefan" to 0
    )
    override suspend fun getDeviceList(device: String): DeviceDto {
        if (apiUpdate == 0) {
            apiUpdate = api.getLastUpdate().toIntUpdate()
        }
        if (storedUpdate.getOrDefault(device, 0) == 0) {
            val storedDeviceUpdates = assemblyDeviceDao.loadDeviceUpdate(device)
            if (storedDeviceUpdates.isNotEmpty()) {
                storedUpdate.put(device, storedDeviceUpdates.first().update)
            }
        }
        if (storedUpdate.getOrDefault(device, 0) < apiUpdate) {
            val dto = api.getDeviceList(device)
            dto.toDevice().forEach { assemblyDeviceDao.insertDevice(it) }
            assemblyDeviceDao.insertDeviceUpdate(DeviceUpdate(device, apiUpdate))
            return dto
        }
        return assemblyDeviceDao.loadDevice(device).toDeviceDto()
    }

    override suspend fun loadAssembly(assemblyId: Int): List<Assembly> {
        return assemblyDeviceDao.loadAssembly(assemblyId)
    }

    override suspend fun insertAssembly(assembly: Assembly) {
        assemblyDeviceDao.insertAssembly(assembly)
    }

    override suspend fun loadMaxAssemblyId(): Int? {
        return assemblyDeviceDao.loadMaxAssemblyId()
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