package com.vimcar.vehicletracker.domain.usecase

import com.vimcar.vehicletracker.data.repository.VehiclesRepository
import com.vimcar.vehicletracker.domain.model.Vehicle
import io.reactivex.Single
import javax.inject.Inject

class LoadVehiclesUseCase @Inject constructor(private val repository: VehiclesRepository) {

    operator fun invoke(): Single<List<Vehicle>> {
        return repository.getVehicles().map { vehicles ->
            vehicles.sortedWith(compareBy({ it.brand }, { it.model }))
        }
    }
}
