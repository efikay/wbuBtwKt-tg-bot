package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.bot.GetCurrentMonthWorkCalendarResponse
import com.efikay.wbubtw.utils.DateUtils
import com.efikay.wbubtw.work_calendar.WorkCalendarService
import org.springframework.stereotype.Service

@Service
class WorkCalendarBotService(
    private val workCalendarService: WorkCalendarService,
) {
    fun getCurrentMonthWorkCalendar(): GetCurrentMonthWorkCalendarResponse {
        val currentMonth = DateUtils.getCurrentMonth()

        return GetCurrentMonthWorkCalendarResponse(
            days = workCalendarService.getMonthDays(currentMonth),
            currentMonth = currentMonth,
        )
    }
}