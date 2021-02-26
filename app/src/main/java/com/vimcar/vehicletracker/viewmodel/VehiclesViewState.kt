package com.vimcar.vehicletracker.viewmodel

import com.vimcar.vehicletracker.domain.model.Vehicle

sealed class VehiclesViewState {
    data class Success(val vehicles: List<Vehicle>) : VehiclesViewState()
    object Loading : VehiclesViewState()
    object Error : VehiclesViewState()
}
