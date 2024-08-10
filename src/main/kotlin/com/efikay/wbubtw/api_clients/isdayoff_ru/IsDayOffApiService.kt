package com.efikay.wbubtw.api_clients.isdayoff_ru

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.time.Month
import java.time.Year

@Service
class IsDayOffApiService(
    val restClient: RestClient
) {
    private val logger = LoggerFactory.getLogger(IsDayOffApiService::class.java)
    private val BASE_URL = "https://isdayoff.ru/api/getdata"

    fun getMonthCalendar(month: Month, year: Year): GetMonthCalendarResponse {
        val queryString = "?year=$year&month=${month.value}"

        logger.info(BASE_URL + queryString)

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