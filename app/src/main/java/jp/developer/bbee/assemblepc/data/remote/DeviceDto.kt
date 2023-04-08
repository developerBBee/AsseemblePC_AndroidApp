package jp.developer.bbee.assemblepc.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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