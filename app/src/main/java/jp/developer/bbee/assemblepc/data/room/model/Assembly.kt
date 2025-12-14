package jp.developer.bbee.assemblepc.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.developer.bbee.assemblepc.domain.model.Price

@Entity
data class Assembly(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assemblyId: Int,
    val assemblyName: String,
    val deviceId: String,
    val deviceType: String,
    val deviceName : String,
    val deviceImgUrl: String,
    val deviceDetail: String,
    val devicePriceSaved: Price,
    val devicePriceRecent: Price,
)
