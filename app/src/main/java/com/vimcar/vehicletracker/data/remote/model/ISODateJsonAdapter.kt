package com.vimcar.vehicletracker.data.remote.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ISODateJsonAdapter : JsonAdapter<LocalDateTime>() {

    private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    override fun fromJson(reader: JsonReader): LocalDateTime? {
        var dateTime: LocalDateTime? = null
        try {
            val dateString = reader.nextString()
            val isTimestamp = dateString.toLongOrNull() != null
            if (isTimestamp) {
                val timestamp = dateString.toLong()
                dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp),
                    ZoneId.systemDefault()
                )
            } else {
                dateTime = LocalDateTime.parse(dateString, dateTimeFormatter)
            }

        } catch (exception: JsonDataException) {
            return dateTime
        }
        return dateTime
    }

    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.format(dateTimeFormatter))
        }
    }
}

