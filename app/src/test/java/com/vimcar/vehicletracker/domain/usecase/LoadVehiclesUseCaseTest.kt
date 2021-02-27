package com.vimcar.vehicletracker.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vimcar.vehicletracker.data.repository.VehiclesRepository
import com.vimcar.vehicletracker.vehiclesList
import io.reactivex.Single
import org.junit.Test

class LoadVehiclesUseCaseTest {

    private val repository: VehiclesRepository = mock()
    private val usecase = LoadVehiclesUseCase(repository)

    @Test
    fun `should return sorted vehicles list`() {
        whenever(repository.getVehicles()).thenReturn(Single.just(vehiclesList))

        usecase().test()
            .assertNoErrors()
            .assertValue(vehiclesList.reversed())
    }
}
