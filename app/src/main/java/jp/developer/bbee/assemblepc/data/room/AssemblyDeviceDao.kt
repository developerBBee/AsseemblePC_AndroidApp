package jp.developer.bbee.assemblepc.data.room

import androidx.room.*
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate

@Dao
interface AssemblyDeviceDao {

    /**
     * Device Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: Device)

    @Query("SELECT * FROM Device WHERE device = :device")
    suspend fun loadDevice(device: String): List<Device>

    @Query("SELECT * FROM Device WHERE id IN (:ids)")
    suspend fun loadDeviceByIds(ids: List<String>): List<Device>

    /**
     * Assembly Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssemblies(assemblies: List<Assembly>)

    @Query("SELECT * FROM Assembly WHERE assemblyId = :assemblyId")
    suspend fun loadAssembly(assemblyId: Int): List<Assembly>

    @Query("SELECT * FROM Assembly ORDER BY assemblyId DESC")
    suspend fun loadAllAssembly(): List<Assembly>

    @Delete
    suspend fun deleteAssembly(assemblies: List<Assembly>)

    @Query("DELETE FROM Assembly WHERE assemblyId = :assemblyId")
    suspend fun deleteAssemblyById(assemblyId: Int)

    @Query("UPDATE Assembly SET assemblyName = :assemblyName WHERE assemblyId = :assemblyId")
    suspend fun renameAssemblyById(assemblyName: String, assemblyId: Int)

    @Query("SELECT MAX(assemblyId) FROM Assembly")
    suspend fun loadMaxAssemblyId(): Int?

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
                Assembly.deviceImgUrl,
                Device.detail AS deviceDetail,
                Assembly.devicePriceSaved,
                Device.price AS devicePriceRecent
            FROM Assembly
            INNER JOIN Device
            ON Assembly.deviceId = Device.id
            WHERE assemblyId = :assemblyId
        """
    )
    suspend fun loadAssemblyNewPrice(assemblyId: Int): List<Assembly>

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