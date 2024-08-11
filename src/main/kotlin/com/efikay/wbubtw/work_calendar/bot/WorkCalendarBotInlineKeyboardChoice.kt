package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.utils.extensions.toRussianString
import kotlinx.datetime.Month
import java.time.Year

class WorkCalendarBotInlineKeyboardChoice private constructor(
    month: Month,
    year: Year,
    val text: String,
) {
    val callbackQueryData = "$CB_DATA_PREFIX/$year/${month.value}"

    companion object {
        fun decodeYearMonthOrNull(callbackQueryData: String): Pair<Year, Month>? {
            val data = callbackQueryData.substringAfter("$CB_DATA_PREFIX/")

            val (yearString, monthString) = data.split('/')

            try {
                val year = Year.of(yearString.toInt())
                val month = Month.of(monthString.toInt())

                return Pair(year, month)
            } catch (_: Exception) {
                return null
            }
        }

        fun choices(
            currentYear: Year,
            currentMonth: Month,
        ) = Pair(
            previous(currentYear, currentMonth),
            next(currentYear, currentMonth),
        )

        private fun previous(
            currentYear: Year,
            currentMonth: Month
        ): WorkCalendarBotInlineKeyboardChoice {
            val prevChoiceMonth = when {
                currentMonth == Month.JANUARY -> Month.DECEMBER
                else -> currentMonth - 1
            }
            val prevChoiceYear = when (prevChoiceMonth) {
                Month.DECEMBER -> currentYear.minusYears(1)
                else -> currentYear
            }
            val prevChoiceText = when (prevChoiceYear) {
                currentYear -> "⬅️ ${prevChoiceMonth.toRussianString()}"
                else -> "⬅️ ($prevChoiceYear) ${prevChoiceMonth.toRussianString()}"
            }

            return WorkCalendarBotInlineKeyboardChoice(
                prevChoiceMonth,
                prevChoiceYear,
                prevChoiceText,
            )
        }

        private fun next(
            currentYear: Year,
            currentMonth: Month
        ): WorkCalendarBotInlineKeyboardChoice {
            val nextChoiceMonth = when {
                currentMonth == Month.DECEMBER -> Month.JANUARY
                else -> currentMonth + 1
            }
            val nextChoiceYear = when (nextChoiceMonth) {
                Month.JANUARY -> currentYear.plusYears(1)
                else -> currentYear
            }
            val nextChoiceText = when (nextChoiceYear) {
                currentYear -> "${nextChoiceMonth.toRussianString()} ➡️"
                else -> "${nextChoiceMonth.toRussianString()} ($nextChoiceYear) ➡️"
            }

            return WorkCalendarBotInlineKeyboardChoice(
                nextChoiceMonth,
                nextChoiceYear,
                nextChoiceText,
            )
        }

        private const val CB_DATA_PREFIX = "wc"
    }
}