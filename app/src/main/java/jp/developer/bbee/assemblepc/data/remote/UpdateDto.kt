package jp.developer.bbee.assemblepc.data.remote


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateDto(
    val kakakuupdate: Int?
)

fun UpdateDto.toIntUpdate(): Int {
    return kakakuupdate ?: 0
}