package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddAssemblyUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val currentRepository: CurrentCompositionRepository,
) {
    suspend operator fun invoke(assembly: Assembly) {
        deviceRepository.insertAssembly(assembly)

        val composition = currentRepository.currentCompositionFlow.first()
        if (composition?.assemblyId == assembly.assemblyId) {
            val assemblies = deviceRepository.loadAssembly(assembly.assemblyId)
            currentRepository.saveCurrentComposition(
                composition.copy(items = assemblies)
            )
        }
    }
}