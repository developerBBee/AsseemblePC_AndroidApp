package jp.developer.bbee.assemblepc.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface DeviceApi {
    @GET("/api/devicelist")
    suspend fun getDeviceList(@Query("device") device: String): DeviceDto
}