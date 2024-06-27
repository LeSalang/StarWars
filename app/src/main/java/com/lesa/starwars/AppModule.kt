package com.lesa.starwars

import com.lesa.api.SWApi
import com.lesa.api.createSWApi
import com.lesa.data.SWRepository
import com.lesa.data.SWRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(okHttpClient: OkHttpClient?): SWApi {
        return createSWApi(
            baseUrl = BuildConfig.SW_API_BASE_URL,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(swApi: SWApi): SWRepository {
        return SWRepositoryImpl(swApi)
    }
}
