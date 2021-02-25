package com.vimcar.vehicletracker.domain.model

import com.vimcar.vehicletracker.data.remote.model.VehicleResponse

object RemoteModelConverter {

    fun VehicleResponse.toVehicle(): Vehicle {
        return Vehicle(
            id = this.id,
            licensePlate = this.licensePlate,
            brand = this.brand,
            model = this.model,
            nickname = this.nickName,
            lastPosition = VehicleLastPosition(
                date = this.lastPosition.timestamp,
                latitude = this.lastPosition.latitude,
                longitude = this.lastPosition.longitude
            )

        )
    }
}
