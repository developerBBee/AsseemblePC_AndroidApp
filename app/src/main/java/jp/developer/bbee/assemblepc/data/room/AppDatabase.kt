package jp.developer.bbee.assemblepc.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate

@Database(
    entities = [Device::class, Assembly::class, DeviceUpdate::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAssemblyDeviceDao(): AssemblyDeviceDao
}