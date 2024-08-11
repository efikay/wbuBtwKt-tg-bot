package com.efikay.wbubtw.domain.work_calendar.bot

data class GetMonthWorkCalendarResponse(
    val formattedCalendar: String,
    val choices: Pair<WorkCalendarBotInlineKeyboardChoice, WorkCalendarBotInlineKeyboardChoice>,
)
