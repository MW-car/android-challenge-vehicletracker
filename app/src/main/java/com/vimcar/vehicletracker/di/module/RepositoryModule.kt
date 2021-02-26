package com.vimcar.vehicletracker.di.module

import com.vimcar.vehicletracker.data.remote.api.VehicleApi
import com.vimcar.vehicletracker.data.repository.VehiclesRepository
import com.vimcar.vehicletracker.data.repository.VehiclesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideVehiclesRepository(api: VehicleApi): VehiclesRepository = VehiclesRepositoryImpl(api)
}
