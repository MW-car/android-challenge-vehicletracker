package com.vimcar.vehicletracker

import com.vimcar.vehicletracker.data.remote.model.LastPosition
import com.vimcar.vehicletracker.data.remote.model.VehicleResponse
import com.vimcar.vehicletracker.domain.model.Vehicle
import com.vimcar.vehicletracker.domain.model.VehicleLastPosition
import java.time.LocalDateTime

private const val ID1 = 1
private const val LICENSE_PLATE1 = "123"
private const val BRAND1 = "BMW"
private const val MODEL1 = "M3"
private val nickName1 = null
private val date1 = LocalDateTime.of(2021, 2, 25, 20, 25)
private const val LATITUDE1 = 53.485186
private const val LONGITUDE1 = 10.059922

private const val ID2 = 2
private const val LICENSE_PLATE2 = "456"
private const val BRAND2 = "Audi"
private const val MODEL2 = "A8"
private const val NICKNAME2 = "My favorite car"
private val date2 = LocalDateTime.of(2021, 2, 26, 20, 25)
private const val LATITUDE2 = 43.485186
private const val LONGITUDE2 = 12.059922

val vehiclesApiResponse = listOf(
    VehicleResponse(
        id = ID1,
        licensePlate = LICENSE_PLATE1,
        brand = BRAND1,
        model = MODEL1,
        nickName = null,
        lastPosition = LastPosition(
            timestamp = date1,
            latitude = LATITUDE1,
            longitude = LONGITUDE1
        )
    ),
    VehicleResponse(
        id = ID2,
        licensePlate = LICENSE_PLATE2,
        brand = BRAND2,
        model = MODEL2,
        nickName = NICKNAME2,
        lastPosition = LastPosition(
            timestamp = date2,
            latitude = LATITUDE2,
            longitude = LONGITUDE2
        )
    )
)

val vehiclesList = listOf(
    Vehicle(
        id = ID1,
        licensePlate = LICENSE_PLATE1,
        brand = BRAND1,
        model = MODEL1,
        nickname = nickName1,
        lastPosition = VehicleLastPosition(
            date = date1,
            latitude = LATITUDE1,
            longitude = LONGITUDE1
        )
    ),
    Vehicle(
        id = ID2,
        licensePlate = LICENSE_PLATE2,
        brand = BRAND2,
        model = MODEL2,
        nickname = NICKNAME2,
        lastPosition = VehicleLastPosition(
            date = date2,
            latitude = LATITUDE2,
            longitude = LONGITUDE2
        )
    )
)
