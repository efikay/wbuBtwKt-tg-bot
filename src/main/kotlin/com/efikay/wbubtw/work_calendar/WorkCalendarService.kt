package com.efikay.wbubtw.work_calendar

import com.efikay.wbubtw.work_calendar.is_day_off_api.IsDayOffApiService
import kotlinx.datetime.Month
import org.springframework.stereotype.Service
import java.time.Year

@Service
class WorkCalendarService(
    private val dayOffApiService: IsDayOffApiService,
    private val repository: WorkCalendarRepository,
) {
    fun getMonthDays(month: Month, year: Year = Year.now()): List<WorkCalendarDay> {
        if (!repository.hasMonthDays(month, year)) {
            val (_, days) = dayOffApiService.getMonthCalendar(month, year)

            if (days == null) {
                throw RuntimeException("Cannot not fetch month days :( Valve pls fix")
            }

            return repository.insertAndGetMonthDays(month, year, days)
        }

        return repository.getMonthDays(month, year)!!
    }
}