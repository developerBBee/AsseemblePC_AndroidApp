package jp.developer.bbee.assemblepc.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Assembly(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assemblyId: Int,
    var assemblyName: String,
    val deviceId: String,
    val deviceType: String,
    val deviceName : String,
    val devicePriceSaved: Int,
    val devicePriceRecent: Int
)
