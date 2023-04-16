package jp.developer.bbee.assemblepc.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.developer.bbee.assemblepc.data.remote.DeviceDto
import jp.developer.bbee.assemblepc.data.remote.Result

@Entity
data class Device(
    @PrimaryKey val id: String,
    val device: String,
    val name: String,
    val imgurl: String,
    val url: String,
    val detail: String,
    val price: Int,
    val rank: Int,
    val releasedate: String,
    val invisible: Boolean,
    val flag1: Int?,
    val flag2: Int?,
    val createddate: String?,
    val lastupdate: String?
)

fun List<Device>.toDeviceDto() : DeviceDto {
    return DeviceDto(
        this.map {
            Result(
                createddate = it.createddate,
                detail = it.detail,
                device = it.device,
                flag1 = it.flag1,
                flag2 = it.flag2,
                id = it.id,
                imgurl = it.imgurl,
                invisible = if (it.invisible) 1 else 0,
                lastupdate = it.lastupdate,
                name = it.name,
                price = it.price,
                rank = it.rank,
                releasedate = it.releasedate,
                url = it.url
            )
        }
    )
}
