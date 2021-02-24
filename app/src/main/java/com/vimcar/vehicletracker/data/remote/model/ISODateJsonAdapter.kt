package com.vimcar.vehicletracker.data.remote.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ISODateJsonAdapter : JsonAdapter<LocalDateTime>() {

    private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    override fun fromJson(reader: JsonReader): LocalDateTime? {
        val dateString = reader.nextString()
        return LocalDateTime.parse(dateString, dateTimeFormatter)
    }

    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.format(dateTimeFormatter))
        }
    }
}

