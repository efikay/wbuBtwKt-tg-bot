package com.efikay.wbubtw.bot

import com.efikay.wbubtw.random.service.RandomService
import com.efikay.wbubtw.utils.DateUtils
import com.efikay.wbubtw.work_calendar.WorkCalendarService
import org.springframework.stereotype.Service

@Service
class BotService(
    private val randomService: RandomService,
    private val workCalendarService: WorkCalendarService,
) {
    fun getRngStatus() = randomService.getInfo()

    fun getCurrentMonthWorkCalendar(): GetCurrentMonthWorkCalendarResponse {
        val currentMonth = DateUtils.getCurrentMonth()

        return GetCurrentMonthWorkCalendarResponse(
            days = workCalendarService.getMonthDays(currentMonth),
            currentMonth = currentMonth,
        )
    }
}