package com.efikay.wbubtw.shared.api_clients.isdayoff_ru

import kotlinx.datetime.Month

enum class DayApiType(val code: Int) {
    WORKING_DAY(0),
    NON_WORKING_DAY(1),
    SHORTENED_DAY(2);

    companion object {
        fun fromInt(code: Int) = entries.first { it.code == code }
    }
}

enum class DayTypeApiErrorCode(val httpCode: Int) {
    DATE_ERROR(400),
    DATE_NOT_FOUND(404),
    SERVICE_ERROR(199)
}

data class GetMonthCalendarResponse(
    val month: Month,
    val days: List<DayApiType>?,
)