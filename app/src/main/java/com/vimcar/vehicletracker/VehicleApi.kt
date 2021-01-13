package com.vimcar.vehicletracker

import io.reactivex.Single
import retrofit2.http.GET

private const val BASE_URL =
    "https://gist.githubusercontent.com/pstued/c6396805c6771f33eb2a694f0c4d7c97/raw/4c492a1dbb46c0a717feb2a8ba1f60e134db7659/vehicles.json"

interface VehicleApi {
    @GET("vehicles.json")
    fun getVehicles(): Single<List<VehicleResponseModel>>
}
