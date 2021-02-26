package com.vimcar.vehicletracker.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vimcar.vehicletracker.data.remote.api.VehicleApi
import com.vimcar.vehicletracker.vehiclesApiResponse
import com.vimcar.vehicletracker.vehiclesList
import io.reactivex.Single
import org.junit.Test

class VehiclesRepositoryTest {

    private val api: VehicleApi = mock()
    private val repository = VehiclesRepositoryImpl(api)

    @Test
    fun `when api returns data should return vehicles list`() {
        whenever(api.getVehicles()).thenReturn(Single.just(vehiclesApiResponse))

        repository.getVehicles()
            .test()
            .assertValue(vehiclesList)
            .assertTerminated()
    }
}
