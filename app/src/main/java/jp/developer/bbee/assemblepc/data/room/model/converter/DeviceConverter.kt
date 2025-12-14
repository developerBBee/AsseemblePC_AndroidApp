package jp.developer.bbee.assemblepc.data.room.model.converter

import jp.developer.bbee.assemblepc.data.room.model.Device as DataDevice
import jp.developer.bbee.assemblepc.domain.model.Device

object DeviceConverter {
    fun Device.toData(): DataDevice = DataDevice(
        id = id,
        device = device,
        name = name,
        imgurl = imgurl,
        url = url,
        detail = detail,
        price = price,
        rank = rank,
        releasedate = releasedate,
        invisible = invisible,
        flag1 = flag1,
        flag2 = flag2,
        createddate = createddate,
        lastupdate = lastupdate,
    )

    fun List<Device>.toData(): List<DataDevice> = map { it.toData() }

    private fun DataDevice.toDomain(): Device = Device(
        id = id,
        device = device,
        name = name,
        imgurl = imgurl,
        url = url,
        detail = detail,
        price = price,
        rank = rank,
        releasedate = releasedate,
        invisible = invisible,
        flag1 = flag1,
        flag2 = flag2,
        createddate = createddate,
        lastupdate = lastupdate,
    )

    fun List<DataDevice>.toDomain(): List<Device> = map { it.toDomain() }
}
