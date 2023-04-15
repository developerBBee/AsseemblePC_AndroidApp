package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.common.DatabaseResponse
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.repository.AssemblyRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAssemblyUseCase @Inject constructor(
    private val assemblyRoomRepository: AssemblyRoomRepository
) {
    operator fun invoke(assemblyId: Int): Flow<DatabaseResponse<List<Assembly>>> = flow {
        emit(DatabaseResponse.Loading())
        val result = assemblyRoomRepository.loadAssembly(assemblyId)
    }
}