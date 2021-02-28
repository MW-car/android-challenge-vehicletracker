package com.vimcar.vehicletracker.data.local

import com.vimcar.vehicletracker.data.remote.model.VehicleResponse

class VehiclesCacheImpl : VehiclesCache {

    companion object {
        // TODO naïve implementation for cache, replace with database in real implementation
        private var vehiclesCache = mutableListOf<VehicleResponse>()
    }

    override fun getVehicles(): List<VehicleResponse> {
        return vehiclesCache
    }

    override fun storeVehicles(vehicles: List<VehicleResponse>) {
        vehiclesCache.clear()
        vehiclesCache.addAll(vehicles)
    }
}
