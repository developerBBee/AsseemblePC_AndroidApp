package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import javax.inject.Inject

class ClearCurrentCompositionUseCase @Inject constructor(
    private val currentRepository: CurrentCompositionRepository,
) {

    suspend operator fun invoke() {
        currentRepository.clearCurrentComposition()
    }
}
