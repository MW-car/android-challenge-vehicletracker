package com.vimcar.vehicletracker.viewmodel

import androidx.lifecycle.ViewModel
import com.vimcar.vehicletracker.domain.usecase.LoadVehiclesUseCase
import javax.inject.Inject

class VehicleOverviewViewModel @Inject constructor(
    private val loadVehiclesUseCase: LoadVehiclesUseCase
) : ViewModel() {

}
