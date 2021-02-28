package com.vimcar.vehicletracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class VehicleResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "licensePlate")
    val licensePlate: String,
    @Json(name = "brand")
    val brand: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "nickname")
    val nickName: String? = null,
    @Json(name = "last_position")
    val lastPosition: LastPosition
)

@JsonClass(generateAdapter = true)
data class LastPosition(
    @Json(name = "timestamp")
    val timestamp: LocalDateTime? = null,
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lng")
    val longitude: Double,
)
