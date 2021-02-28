package com.vimcar.vehicletracker.data.local

import com.vimcar.vehicletracker.data.remote.model.VehicleResponse

interface VehiclesCache {

    fun getVehicles(): List<VehicleResponse>

    fun storeVehicles(vehicles: List<VehicleResponse>)
}
