package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class DeleteAssemblyUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    suspend operator fun invoke(assemblyId: Int) {
        try {
            deviceRepository.deleteAssemblyById(assemblyId)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}