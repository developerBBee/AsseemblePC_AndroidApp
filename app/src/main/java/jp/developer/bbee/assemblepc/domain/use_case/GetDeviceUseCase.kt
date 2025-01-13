package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.common.NetworkResponse
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    operator fun invoke(deviceType: DeviceType): Flow<NetworkResponse<List<Device>>> = flow {
        try {
            emit(NetworkResponse.Loading())
            val result = deviceRepository.getDeviceList(deviceType)
            emit(NetworkResponse.Success(result))
        } catch (e: java.lang.Exception) {
            emit(NetworkResponse.Failure(e.message.toString()))
        }
    }
}