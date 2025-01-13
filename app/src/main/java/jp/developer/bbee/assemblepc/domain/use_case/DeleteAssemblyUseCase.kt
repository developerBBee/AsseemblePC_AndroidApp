package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteAssemblyUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val currentRepository: CurrentCompositionRepository,
) {

    suspend operator fun invoke(assemblyId: Int) {
        deviceRepository.deleteAssemblyById(assemblyId)
        val composition = currentRepository.currentCompositionFlow.first()
        if (composition?.assemblyId == assemblyId) {
            currentRepository.clearCurrentComposition()
        }
    }
}
