package jp.developer.bbee.assemblepc.data.room.model.converter

import jp.developer.bbee.assemblepc.data.room.model.Assembly as DataAssembly
import jp.developer.bbee.assemblepc.domain.model.Assembly

object AssemblyConverter {
    private fun Assembly.toData(): DataAssembly = DataAssembly(
        id = id,
        assemblyId = assemblyId,
        assemblyName = assemblyName,
        deviceId = deviceId,
        deviceType = deviceType,
        deviceName = deviceName,
        deviceImgUrl = deviceImgUrl,
        deviceDetail = deviceDetail,
        devicePriceSaved = devicePriceSaved,
        devicePriceRecent = devicePriceRecent,
    )

    fun List<Assembly>.toData(): List<DataAssembly> = map { it.toData() }

    private fun DataAssembly.toDomain(): Assembly = Assembly(
        id = id,
        assemblyId = assemblyId,
        assemblyName = assemblyName,
        deviceId = deviceId,
        deviceType = deviceType,
        deviceName = deviceName,
        deviceImgUrl = deviceImgUrl,
        deviceDetail = deviceDetail,
        devicePriceSaved = devicePriceSaved,
        devicePriceRecent = devicePriceRecent,
    )

    fun List<DataAssembly>.toDomain(): List<Assembly> = map { it.toDomain() }
}
