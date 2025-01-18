package jp.developer.bbee.assemblepc.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
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

fun List<Assembly>.toCompositionItems(
    assemblyId: Int,
    devices: List<Device>
): List<CompositionItem> =
    filter { it.assemblyId == assemblyId }
        .groupingBy { it.deviceId }
        .eachCount()
        .mapNotNull { (id, quantity) ->
            devices.find { it.id == id }
                ?.let { device -> device to quantity }
        }
        .map { (device, quantity) ->
            CompositionItem.of(
                quantity = quantity,
                device = device
            )
        }
