package com.vimcar.vehicletracker.di.module

import com.squareup.moshi.Moshi
import com.vimcar.vehicletracker.BuildConfig
import com.vimcar.vehicletracker.data.remote.model.ISODateJsonAdapter
import com.vimcar.vehicletracker.data.remote.api.VehicleApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

private const val API_BASE_URL =
    "https://gist.githubusercontent.com/pstued/c6396805c6771f33eb2a694f0c4d7c97/raw/4c492a1dbb46c0a717feb2a8ba1f60e134db7659/"

@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateTime::class.java, ISODateJsonAdapter())
            .build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesVehicleApi(retrofit: Retrofit): VehicleApi {
        return retrofit.create(VehicleApi::class.java)
    }
}

