package com.vimcar.vehicletracker.data.remote.api

import com.vimcar.vehicletracker.data.remote.model.VehicleResponse
import io.reactivex.Single
import retrofit2.http.GET



interface VehicleApi {
    @GET("vehicles.json")
    fun getVehicles(): Single<List<VehicleResponse>>
}
