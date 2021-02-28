package com.vimcar.vehicletracker.di.module

import com.vimcar.vehicletracker.data.local.VehiclesCache
import com.vimcar.vehicletracker.data.local.VehiclesCacheImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CacheModule {

    @Singleton
    @Provides
    fun provideCache(): VehiclesCache = VehiclesCacheImpl()
}
