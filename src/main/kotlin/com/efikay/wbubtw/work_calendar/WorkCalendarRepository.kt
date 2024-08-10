package com.efikay.wbubtw.work_calendar

import com.efikay.wbubtw.api_clients.isdayoff_ru.DayApiType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.springframework.stereotype.Repository
import java.time.Year


@Repository
class WorkCalendarRepository {
    private val years = HashMap<Year, MutableMap<Month, List<WorkCalendarDay>>>()

    fun insertAndGetMonthDays(
        month: Month,
        year: Year,
        data: List<DayApiType>,
    ): List<WorkCalendarDay> {
        if (years[year] == null) {
            years[year] = mutableMapOf()
        }

        val monthDays = data.mapIndexed { dayIndex, apiDayType ->
            val dayDate = LocalDate(year.value, month, dayIndex + 1)

            WorkCalendarDay.from(dayDate, apiDayType)
        }

        years[year]!![month] = monthDays

        return monthDays
    }

    fun hasMonthDays(month: Month, year: Year) = years[year]?.containsKey(month) ?: false

    fun getMonthDays(month: Month, year: Year): List<WorkCalendarDay>? {
        val yearMonths = years[year] ?: return null

        return yearMonths[month]
    }
}
