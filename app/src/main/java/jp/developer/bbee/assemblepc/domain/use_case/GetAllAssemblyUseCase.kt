package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class GetAllAssemblyUseCase @Inject constructor(
    private val repository: DeviceRepository
){
    suspend operator fun invoke(): List<Assembly> =
        repository.loadAllAssembly()
}