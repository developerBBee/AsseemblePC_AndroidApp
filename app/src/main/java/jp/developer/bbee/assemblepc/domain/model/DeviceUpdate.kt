package jp.developer.bbee.assemblepc.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceUpdate(
    @PrimaryKey val device: String,
    val update: Int // format example: 20230101
)
