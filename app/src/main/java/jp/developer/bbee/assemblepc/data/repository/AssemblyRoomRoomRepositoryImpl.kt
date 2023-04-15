package jp.developer.bbee.assemblepc.data.repository

import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.repository.AssemblyRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssemblyRoomRoomRepositoryImpl @Inject constructor(
    val assemblyDeviceDao: AssemblyDeviceDao
) : AssemblyRoomRepository {
    override fun loadAssembly(assemblyId: Int): Flow<List<Assembly>> {
        return assemblyDeviceDao.loadAssembly(assemblyId)
    }

    override suspend fun insertAssembly(assembly: Assembly) {
        assemblyDeviceDao.insertAssembly(assembly)
    }
}