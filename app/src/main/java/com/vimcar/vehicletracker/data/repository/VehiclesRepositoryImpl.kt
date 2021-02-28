package com.vimcar.vehicletracker.data.repository

import com.vimcar.vehicletracker.data.local.VehiclesCache
import com.vimcar.vehicletracker.data.remote.api.VehicleApi
import com.vimcar.vehicletracker.domain.model.RemoteModelConverter.toVehicle
import com.vimcar.vehicletracker.domain.model.Vehicle
import io.reactivex.Single
import javax.inject.Inject

class VehiclesRepositoryImpl @Inject constructor(
    private val api: VehicleApi,
    private val cache: VehiclesCache
) : VehiclesRepository {

    override fun getVehicles(): Single<List<Vehicle>> {
        val vehicles = cache.getVehicles()
        return if (vehicles.isNotEmpty()) {
            Single.just(vehicles)
        } else {
            api.getVehicles().doOnSuccess { cache.storeVehicles(it) }
        }.map { vehiclesList ->
            vehiclesList.map { it.toVehicle() }
        }
    }
}
