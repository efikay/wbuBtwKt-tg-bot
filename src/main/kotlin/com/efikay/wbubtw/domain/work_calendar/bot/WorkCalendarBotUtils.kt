package com.efikay.wbubtw.domain.work_calendar.bot

import com.efikay.wbubtw.shared.utils.DateUtils
import com.efikay.wbubtw.shared.utils.extensions.toRussianString
import com.efikay.wbubtw.domain.work_calendar.WorkCalendarDay
import com.efikay.wbubtw.domain.work_calendar.WorkCalendarDayType
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import java.time.Year

class WorkCalendarBotUtils {
    companion object {
        fun formatCalendarTips(
            calendarYear: Year
        ): String {
            val today = DateUtils.getToday()

            val todayYear = today.year
            val todayMonth = today.month
            val todayDay = today.dayOfMonth

            val isCurrentYearCalendar = calendarYear.value == todayYear

            val todayIsLegend = if (isCurrentYearCalendar) {
                "*Сегодня* – $todayDay ${todayMonth.toRussianString()}"
            } else {
                "*Сегодня* – $todayDay ${todayMonth.toRussianString()} $todayYear"
            }

            return """
                |Легенда:
                |\- ` $todayDay` – *Рабочий день* ⚒️  
                |\- ` ${todayDay.toString().toCharArray().joinToString("") { "$it\u0336" }}` – *Нерабочий день* 🏖️
                |\- `*$todayDay` – *Сегодняшний день* 🕰️
                |
                |$todayIsLegend
            """.trimMargin()
        }

        fun formatMonthWorkCalendar(year: Year, month: Month, days: List<WorkCalendarDay>): String {
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

            val firstWeekGap = (1..(7 - weeks.first().size)).joinToString("") { "`    `" }

            val header = formatWorkCalendarHeader(month)

            return """
$header
            |${firstWeekGap}${
                weeks.joinToString("\n") { week ->
                    week.joinToString("` `") { day ->
                        formatWorkCalendarDay(
                            day,
                            isToday = isWorkCalendarDayToday(year, month, day)
                        )
                    }
                }
            }
            """.trimIndent().trimMargin()
        }

        private fun isWorkCalendarDayToday(year: Year, month: Month, day: WorkCalendarDay): Boolean {
            val today = DateUtils.getToday().date

            return (
                    today.month == month &&
                            today.year == year.value &&
                            day.day == today.dayOfMonth
                    )
        }

        private fun formatWorkCalendarHeader(month: Month): String {
            assert(CALENDAR_DAYS_HEADER.length == CALENDAR_HEADER_BORDER_BOTTOM.length) {
                "Calendar header and header bottom border must be of equal size"
            }

            val monthDisplayName = month.toRussianString()

            val monthLeftMargin = (CALENDAR_DAYS_HEADER.length - monthDisplayName.length) / 2
            val monthDisplayMargin = (1..monthLeftMargin).joinToString("") { " " }

            return """
                |`$monthDisplayMargin$monthDisplayName`
                |`$CALENDAR_DAYS_HEADER`
                |`$CALENDAR_HEADER_BORDER_BOTTOM`
            """.trimIndent()
        }

        private fun formatWorkCalendarDay(day: WorkCalendarDay, isToday: Boolean): String {
            val formattedDay = when (day.dayType) {
                // TODO: Different formatting for SHORTENED_DAY
                WorkCalendarDayType.WORKING_DAY, WorkCalendarDayType.SHORTENED_DAY -> "`${day.day}`"
                WorkCalendarDayType.NON_WORKING_DAY -> {
                    val crossedText = day.day.toString().toCharArray().joinToString("") { "$it\u0336" }

                    "`$crossedText`"
                }
            }

            val specialMark = if (isToday) {
                "*"
            } else {
                " "
            }
            val prefixMeta = when {
                day.day >= 10 -> specialMark
                else -> " $specialMark"
            }

            return "`$prefixMeta`$formattedDay"
        }

        private const val CALENDAR_DAYS_HEADER = " ПН  ВТ  СР  ЧТ  ПТ  СБ  ВС"
        private const val CALENDAR_HEADER_BORDER_BOTTOM = "--- --- --- --- --- --- ---"
    }
}