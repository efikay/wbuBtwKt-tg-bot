package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.work_calendar.WorkCalendarService
import kotlinx.datetime.Month
import org.springframework.stereotype.Service
import java.time.Year

@Service
class WorkCalendarBotService(
    private val workCalendarService: WorkCalendarService,
) {
    fun getMonthWorkCalendar(month: Month, year: Year): GetMonthWorkCalendarResponse {
        val days = workCalendarService.getMonthDays(month, year)

        return GetMonthWorkCalendarResponse(
            formattedCalendar = WorkCalendarBotUtils.formatMonthWorkCalendar(year, month, days),
            choices = WorkCalendarBotInlineKeyboardChoice.choices(
                year,
                month,
            ),
        )
    }
}