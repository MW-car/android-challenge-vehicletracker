package com.vimcar.vehicletracker.data.remote.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDateTime


class ISODateJsonAdapterTest {

    private val moshi = Moshi.Builder()
        .add(LocalDateTime::class.java, ISODateJsonAdapter().lenient())
        .build()

    private val adapter: JsonAdapter<LocalDateTime> = moshi.adapter(LocalDateTime::class.java)

    @Test
    fun `should parse date time strings`() {
        val json = "\"2020-12-15T12:12:22Z\""
        val actual = adapter.fromJson(json)
        val expected = LocalDateTime.of(2020, 12, 15, 12, 12, 22)
        assertEquals(expected, actual)
    }

    @Test
    fun `should parse timestamp strings`() {
        val json = "1609504496"
        val actual = adapter.fromJson(json)
        val expected = LocalDateTime.of(2021, 1, 1, 12, 34, 56)
        assertEquals(expected, actual)
    }

    @Test
    fun `when json is null should return null date time object`() {
        val json = "null"
        val actual = adapter.fromJson(json)
        assertThat(actual).isNull()
    }
}
