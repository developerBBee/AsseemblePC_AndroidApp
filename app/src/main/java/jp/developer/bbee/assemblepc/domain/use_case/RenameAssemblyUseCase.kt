package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class RenameAssemblyUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    suspend operator fun invoke(assemblyName: String, assemblyId: Int) {
        try {
            deviceRepository.renameAssemblyById(assemblyName, assemblyId)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}