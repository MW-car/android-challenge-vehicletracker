package com.vimcar.vehicletracker.domain.model

import java.time.LocalDateTime

data class VehicleLastPosition(
    val date: LocalDateTime? = null,
    val latitude: Double,
    val longitude: Double
)
