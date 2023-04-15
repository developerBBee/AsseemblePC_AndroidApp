package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.Assembly
import kotlinx.coroutines.flow.Flow

interface AssemblyRoomRepository {
    fun loadAssembly(assemblyId: Int): Flow<List<Assembly>>
    suspend fun insertAssembly(assembly: Assembly)
}