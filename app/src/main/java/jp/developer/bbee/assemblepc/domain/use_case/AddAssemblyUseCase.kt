package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class AddAssemblyUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(assembly: Assembly) {
        try {
            deviceRepository.insertAssembly(assembly)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}