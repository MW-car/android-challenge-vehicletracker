package com.vimcar.vehicletracker.domain.model

data class Vehicle(
    val id: Int,
    val licensePlate: String,
    val brand: String,
    val model: String,
    val nickname: String? = null,
    val lastPosition: VehicleLastPosition
)
