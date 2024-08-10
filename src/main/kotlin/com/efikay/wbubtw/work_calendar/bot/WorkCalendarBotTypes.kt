package com.efikay.wbubtw.work_calendar.bot

data class GetMonthWorkCalendarResponse(
    val formattedCalendar: String,
    val choices: Pair<WorkCalendarBotInlineKeyboardChoice, WorkCalendarBotInlineKeyboardChoice>,
)
