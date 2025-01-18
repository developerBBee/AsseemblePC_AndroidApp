package jp.developer.bbee.assemblepc.domain.model

import kotlinx.serialization.Serializable

/**
 * 構成情報
 */
@Serializable
data class Composition(
    val assemblyId: Int,
    val assemblyName: String,
    val items: List<CompositionItem>,
) {

    companion object {
        fun of(
            assemblyId: Int,
            assemblyName: String,
            assemblies: List<Assembly>,
            devices: List<Device>
        ): Composition {
            val items = assemblies.toCompositionItems(
                assemblyId = assemblyId,
                devices = devices
            )

            return Composition(
                assemblyId = assemblyId,
                assemblyName = assemblyName,
                items = items
            )
        }
    }
}

@Serializable
data class CompositionItem(
    val quantity: Int,
    val deviceId: String,
    val deviceType: String,
    val deviceName : String,
    val deviceImgUrl: String,
    val deviceDetail: String,
    val devicePriceSaved: Price,
    val devicePriceRecent: Price,
    val url: String,
    val price: Price,
    val rank: Int,
    val releasedate: String,
    val invisible: Boolean,
    val flag1: Int?,
    val flag2: Int?,
    val createddate: String?,
    val lastupdate: String?,
) {

    fun toDevice(): Device {
        return Device(
            id = deviceId,
            device = deviceType,
            name = deviceName,
            imgurl = deviceImgUrl,
            url = url,
            detail = deviceDetail,
            price = price,
            rank = rank,
            releasedate = releasedate,
            invisible = invisible,
            flag1 = flag1,
            flag2 = flag2,
            createddate = createddate,
            lastupdate = lastupdate,
        )
    }

    companion object {
        fun of(
            quantity: Int,
            device: Device
        ): CompositionItem {
            return CompositionItem(
                quantity = quantity,
                deviceId = device.id,
                deviceType = device.device,
                deviceName = device.name,
                deviceImgUrl = device.imgurl,
                deviceDetail = device.detail,
                devicePriceSaved = device.price,
                devicePriceRecent = device.price,
                url = device.url,
                price = device.price,
                rank = device.rank,
                releasedate = device.releasedate,
                invisible = device.invisible,
                flag1 = device.flag1,
                flag2 = device.flag2,
                createddate = device.createddate,
                lastupdate = device.lastupdate,
            )
        }
    }
}
