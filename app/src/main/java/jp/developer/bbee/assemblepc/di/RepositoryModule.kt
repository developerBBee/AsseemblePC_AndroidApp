package jp.developer.bbee.assemblepc.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.developer.bbee.assemblepc.data.remote.DeviceApi
import jp.developer.bbee.assemblepc.data.repository.CurrentCompositionRepositoryImpl
import jp.developer.bbee.assemblepc.data.repository.DeviceRepositoryImpl
import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeviceRepository(
        api: DeviceApi,
        dao: AssemblyDeviceDao
    ): DeviceRepository = DeviceRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideCurrentAssemblyIdRepository(
        @ApplicationContext context: Context,
    ): CurrentCompositionRepository = CurrentCompositionRepositoryImpl(context)
}