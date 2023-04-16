package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.common.DatabaseResponse
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.domain.repository.DeviceRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreDeviceUseCase @Inject constructor(
    private val deviceRoomRepository: DeviceRoomRepository
){
    /**
     * デバイスの更新日時を更新・保存する
     * @param device デバイスタイプ名
     * @param update 更新日時 example "20230101"
     */
    suspend operator fun invoke(device: String, update: String): Flow<DatabaseResponse<DeviceUpdate>> = flow {
        try {
            emit(DatabaseResponse.Loading())
            if (deviceRoomRepository.existDeviceUpdate(device) > 0) {
                val storedUpdate = deviceRoomRepository.loadDeviceUpdate(device).first()
                if (storedUpdate.equals(update)) {
                    // 既に登録済みの場合は何もしない
                    emit(DatabaseResponse.Success(storedUpdate))
                } else {
                    // 保存されている日付と異なる場合は更新する
                    deviceRoomRepository.insertDeviceUpdate(DeviceUpdate(device, update))
                    emit(DatabaseResponse.Success(DeviceUpdate(device, update)))
                }
            } else {
                // 保存されていない場合は新規登録する
                deviceRoomRepository.insertDeviceUpdate(DeviceUpdate(device, update))
            }
        } catch (e: Exception) {
            emit(DatabaseResponse.Failure(e.message.toString()))
        }
    }
}