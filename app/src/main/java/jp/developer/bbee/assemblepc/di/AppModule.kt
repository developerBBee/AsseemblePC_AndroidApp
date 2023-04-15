package jp.developer.bbee.assemblepc.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.developer.bbee.assemblepc.common.Constants.BASE_URL
import jp.developer.bbee.assemblepc.data.remote.DeviceApi
import jp.developer.bbee.assemblepc.data.repository.DeviceRepositoryImpl
import jp.developer.bbee.assemblepc.data.room.AppDatabase
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDeviceApi(): DeviceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build().create(DeviceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(api: DeviceApi): DeviceRepository {
        return DeviceRepositoryImpl(api)
    }

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "assemblepc_database").build()

    @Provides
    fun provideDao(db: AppDatabase) = db.getAssemblyDeviceDao()
}