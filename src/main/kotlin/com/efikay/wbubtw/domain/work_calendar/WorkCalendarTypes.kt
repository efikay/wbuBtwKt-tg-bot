package com.efikay.wbubtw.domain.work_calendar

import com.efikay.wbubtw.shared.api_clients.isdayoff_ru.DayApiType
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate


data class WorkCalendarDay(
    val day: Int,
    val dayOfWeek: DayOfWeek,
    val dayType: WorkCalendarDayType,
) {
    fun isWorkingDay() = dayType.isWorkingDay()
    fun isNonWorkingDay() = dayType.isNonWorkingDay()
    fun isShortenedDay() = dayType.isShortenedDay()

    companion object {
        fun from(date: LocalDate, apiDay: DayApiType) = WorkCalendarDay(
            day = date.dayOfMonth,
            dayOfWeek = date.dayOfWeek,
            dayType = WorkCalendarDayType.fromApiType(apiDay),
        )
    }
}

enum class WorkCalendarDayType {
    WORKING_DAY,
    NON_WORKING_DAY,
    SHORTENED_DAY;

    fun isWorkingDay() = this == WORKING_DAY
    fun isNonWorkingDay() = this == NON_WORKING_DAY
    fun isShortenedDay() = this == SHORTENED_DAY

    companion object {
        fun fromApiType(apiDay: DayApiType) = WorkCalendarDayType.entries.first {
            return when (apiDay) {
                DayApiType.WORKING_DAY -> WORKING_DAY
                DayApiType.NON_WORKING_DAY -> NON_WORKING_DAY
                DayApiType.SHORTENED_DAY -> SHORTENED_DAY
            }
        }
    }
}