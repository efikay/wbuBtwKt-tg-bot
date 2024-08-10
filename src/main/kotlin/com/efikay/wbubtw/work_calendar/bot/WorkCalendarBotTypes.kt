package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.work_calendar.WorkCalendarDay
import kotlinx.datetime.Month

data class GetCurrentMonthWorkCalendarResponse(
    val days: List<WorkCalendarDay>,
    val currentMonth: Month,
)