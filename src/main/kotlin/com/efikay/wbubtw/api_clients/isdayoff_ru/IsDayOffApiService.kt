package com.efikay.wbubtw.api_clients.isdayoff_ru

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.time.Month
import java.time.Year

@Service
class IsDayOffApiService(
    val restClient: RestClient
) {
    private val BASE_URL = "https://isdayoff.ru/api/getdata"

    fun getMonthCalendar(month: Month, year: Year): GetMonthCalendarResponse {
        val queryString = "?year=$year&month=${month.value}"

        val response = restClient.get().uri(BASE_URL + queryString).retrieve().body<String>()
            ?: return GetMonthCalendarResponse(
                month,
                null,
            )

        val numbers = response.toCharArray().map {
            it.digitToInt()
        }

        return GetMonthCalendarResponse(
            month,
            days = numbers.map { DayApiType.fromInt(it) },
        )
    }
}