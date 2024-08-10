package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.work_calendar.WorkCalendarDay
import com.efikay.wbubtw.work_calendar.WorkCalendarDayType
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

class WorkCalendarBotUtils {
    companion object {
        private fun formatWorkCalendarDay(day: WorkCalendarDay): String {
            val formattedDay = when (day.dayType) {
                WorkCalendarDayType.WORKING_DAY -> "`${day.day}`"
                WorkCalendarDayType.NON_WORKING_DAY -> {
                    val crossedText = day.day.toString().toCharArray().joinToString("") { "$it\u0336" }

                    "`$crossedText`"
                }
                // TODO: Different formatting
                WorkCalendarDayType.SHORTENED_DAY -> "`${day.day}`"
            }
            val prefix = when {
                day.day >= 10 -> ""
                else -> "` `"
            }

            return "$prefix$formattedDay"
        }

        // TODO: NOW IT's JUST A CALENDAR. FORMAT TO DIFFER ONE DAY TYPES FROM ANOTHER
        fun formatMonthWorkCalendar(month: Month, days: List<WorkCalendarDay>): String {
            val sortedDays = days.sortedBy { it.day }

            val weeks = mutableListOf<List<WorkCalendarDay>>()
            var currentWeek = mutableListOf<WorkCalendarDay>()
            sortedDays.forEach {
                currentWeek.add(it)

                if (it.dayOfWeek === DayOfWeek.SUNDAY) {
                    weeks.add(currentWeek)
                    currentWeek = mutableListOf()
                }
            }.also {
                if (currentWeek.isNotEmpty()) {
                    weeks.add(currentWeek)
                }
            }

            val firstWeekGap = (1..(7 - weeks.first().size)).joinToString("") { "`   `" }

            return """
            |`      ${month}`
            |`ПН ВТ СР ЧТ ПТ СБ ВС`
            |`-- -- -- -- -- -- --`
            |${firstWeekGap}${
                weeks.joinToString("\n") { week ->
                    week.joinToString("` `") { day ->
                        formatWorkCalendarDay(day)
                    }
                }
            }
            """.trimIndent().trimMargin()
        }
    }
}