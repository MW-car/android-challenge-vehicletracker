package com.vimcar.vehicletracker.data.repository

import com.vimcar.vehicletracker.data.remote.api.VehicleApi
import com.vimcar.vehicletracker.domain.model.RemoteModelConverter.toVehicle
import com.vimcar.vehicletracker.domain.model.Vehicle
import io.reactivex.Single
import javax.inject.Inject

class VehiclesRepositoryImpl @Inject constructor(
    private val api: VehicleApi
) : VehiclesRepository {

    override fun getVehicles(): Single<List<Vehicle>> {
        return api.getVehicles()
            .map { vehiclesList ->
                vehiclesList.map { it.toVehicle() }
            }
    }
}
