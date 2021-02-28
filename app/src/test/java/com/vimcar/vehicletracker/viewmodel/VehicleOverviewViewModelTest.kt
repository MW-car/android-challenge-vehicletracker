package com.vimcar.vehicletracker.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.vimcar.vehicletracker.domain.usecase.LoadVehiclesUseCase
import com.vimcar.vehicletracker.utils.TestReactiveTransformer
import com.vimcar.vehicletracker.vehiclesList
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class VehicleOverviewViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val reactiveTransformer = TestReactiveTransformer()
    private val loadVehiclesUseCase: LoadVehiclesUseCase = mock()

    @Test
    fun `when data is loaded should post success state`() {
        val expectedVehiclesList = vehiclesList
        whenever(loadVehiclesUseCase()).thenReturn(Single.just(expectedVehiclesList))

        val viewModel = VehicleOverviewViewModel(
            loadVehiclesUseCase = loadVehiclesUseCase,
            reactiveTransformer = reactiveTransformer
        )

        val captor = argumentCaptor<VehiclesViewState>()
        val observer: Observer<VehiclesViewState> = mock()

        viewModel.vehiclesOverViewViewState.observeForever(observer)
        viewModel.loadVehicles()

        verify(loadVehiclesUseCase).invoke()
        verify(observer, times(2)).onChanged(captor.capture())

        val values = captor.allValues
        assertEquals(VehiclesViewState.Loading, values[0])
        assertEquals(VehiclesViewState.Success(expectedVehiclesList), values[1])

        viewModel.vehiclesOverViewViewState.removeObserver(observer)
    }

    @Test
    fun `when data loading fails should post error state`() {
        whenever(loadVehiclesUseCase()).thenReturn(Single.error(Throwable()))

        val viewModel = VehicleOverviewViewModel(
            loadVehiclesUseCase = loadVehiclesUseCase,
            reactiveTransformer = reactiveTransformer
        )

        val captor = argumentCaptor<VehiclesViewState>()
        val observer: Observer<VehiclesViewState> = mock()

        viewModel.vehiclesOverViewViewState.observeForever(observer)
        viewModel.loadVehicles()

        verify(loadVehiclesUseCase).invoke()
        verify(observer, times(2)).onChanged(captor.capture())

        val values = captor.allValues
        assertEquals(VehiclesViewState.Loading, values[0])
        assertEquals(VehiclesViewState.Error, values[1])

        viewModel.vehiclesOverViewViewState.removeObserver(observer)
    }

    @Test
    fun `when refreshed should load data`() {
        val expectedVehiclesList1 = vehiclesList
        val expectedVehiclesList2 = vehiclesList + vehiclesList[0]
        whenever(loadVehiclesUseCase())
            .thenReturn(Single.just(expectedVehiclesList1))
            .thenReturn(Single.just(expectedVehiclesList2))

        val viewModel = VehicleOverviewViewModel(
            loadVehiclesUseCase = loadVehiclesUseCase,
            reactiveTransformer = reactiveTransformer
        )

        val captor = argumentCaptor<VehiclesViewState>()
        val observer: Observer<VehiclesViewState> = mock()

        viewModel.vehiclesOverViewViewState.observeForever(observer)
        viewModel.loadVehicles()
        viewModel.refreshVehicles()

        verify(loadVehiclesUseCase, times(2)).invoke()
        verify(observer, times(4)).onChanged(captor.capture())

        val values = captor.allValues
        assertEquals(VehiclesViewState.Loading, values[0])
        assertEquals(VehiclesViewState.Success(expectedVehiclesList1), values[1])
        assertEquals(VehiclesViewState.Loading, values[2])
        assertEquals(VehiclesViewState.Success(expectedVehiclesList2), values[3])

        viewModel.vehiclesOverViewViewState.removeObserver(observer)
    }
}
