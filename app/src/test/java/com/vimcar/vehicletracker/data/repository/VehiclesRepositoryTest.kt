package com.vimcar.vehicletracker.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vimcar.vehicletracker.data.local.VehiclesCache
import com.vimcar.vehicletracker.data.remote.api.VehicleApi
import com.vimcar.vehicletracker.vehiclesApiResponse
import com.vimcar.vehicletracker.vehiclesList
import io.reactivex.Single
import org.junit.Test

class VehiclesRepositoryTest {

    private val api: VehicleApi = mock()
    private val cache: VehiclesCache = mock()
    private val repository = VehiclesRepositoryImpl(api, cache)

    @Test
    fun `when cache is empty should call api and store in cache`() {
        whenever(api.getVehicles()).thenReturn(Single.just(vehiclesApiResponse))
        whenever(cache.getVehicles()).thenReturn(emptyList())

        repository.getVehicles()
            .test()
            .assertValue(vehiclesList)
            .assertTerminated()

        verify(cache).storeVehicles(vehiclesApiResponse)
    }

    @Test
    fun `when cache is not empty should return from cache`() {
        whenever(cache.getVehicles()).thenReturn(vehiclesApiResponse)

        repository.getVehicles()
            .test()
            .assertValue(vehiclesList)
            .assertTerminated()

        verify(api, never()).getVehicles()
    }
}
