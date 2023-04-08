package jp.developer.bbee.assemblepc.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import jp.developer.bbee.assemblepc.domain.model.Device

@JsonClass(generateAdapter = true)
data class DeviceDto(
    val results: List<Result>?
)

@JsonClass(generateAdapter = true)
data class Result(
    val createddate: String?,
    val detail: String?,
    val device: String?,
    val flag1: Int?,
    val flag2: Int?,
    val id: String?,
    val imgurl: String?,
    val invisible: Int?,
    val lastupdate: String?,
    val name: String?,
    val price: Int?,
    val rank: Int?,
    val releasedate: String?,
    val url: String?
)

fun DeviceDto.toDevice(): List<Device> {
    if (results == null) return emptyList()
    return results.map {
        Device(
            id = it.id!!,
            device = it.device!!,
            name = it.name ?: "",
            imgurl = it.imgurl ?: "https://img1.kakaku.k-img.com/images/productimage/l/nowprinting.gif",
            invisible = when (it.invisible) {
                null -> true
                1 -> true
                else -> false
            },
            url = it.url ?: "https://kakaku.com/",
            detail = it.detail ?: "",
            price = it.price ?: 0,
            rank = it.rank ?: 99,
            releasedate = it.releasedate ?: "20000101",
            flag1 = it.flag1,
            flag2 = it.flag2,
            createddate = it.createddate,
            lastupdate = it.lastupdate
        )
    }
}