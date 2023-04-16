package jp.developer.bbee.assemblepc.data.room

import androidx.room.*
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface AssemblyDeviceDao {

    /**
     * Device Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: Device)

    @Query("SELECT * FROM Device WHERE device = :device")
    fun loadDevice(device: String): Flow<List<Device>>

    // 存在チェック 0:存在しない >0:存在する
    @Query("SELECT COUNT(*) FROM Device WHERE id = :id")
    suspend fun existDeviceById(id: String): Int


    /**
     * Assembly Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssembly(assembly: Assembly)

    @Query("SELECT * FROM Assembly WHERE assemblyId = :assemblyId")
    fun loadAssembly(assemblyId: Int): Flow<List<Assembly>>

    @Delete
    suspend fun deleteAssembly(assembly: Assembly)

    @Query("DELETE FROM Assembly WHERE assemblyId = :assemblyId")
    suspend fun deleteAssemblyById(assemblyId: Int)


    /**
     * AssemblyDevice Join
     */
    @Query(
        """
            SELECT
                Assembly.id,
                Assembly.assemblyId,
                Assembly.assemblyName,
                Assembly.deviceId,
                Assembly.deviceType,
                Assembly.deviceName,
                Assembly.devicePriceSaved,
                Device.price AS devicePriceRecent
            FROM Assembly
            INNER JOIN Device
            ON Assembly.deviceId = Device.id
            WHERE assemblyId = :assemblyId
        """
    )
    fun loadAssemblyNewPrice(assemblyId: Int): Flow<List<Assembly>>

    /**
     * DeviceUpdate Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate)

    @Query("SELECT * FROM DeviceUpdate WHERE device = :device")
    suspend fun loadDeviceUpdate(device: String): List<DeviceUpdate>

    // 存在チェック 0:存在しない >0:存在する
    @Query("SELECT COUNT(*) FROM DeviceUpdate WHERE device = :device")
    suspend fun existDeviceUpdate(device: String): Int
}