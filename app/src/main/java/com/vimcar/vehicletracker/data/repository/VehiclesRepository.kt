package com.vimcar.vehicletracker.data.repository

import com.vimcar.vehicletracker.domain.model.Vehicle
import io.reactivex.Single

interface VehiclesRepository {

    fun getVehicles(): Single<List<Vehicle>>
}
