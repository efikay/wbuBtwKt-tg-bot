package com.efikay.wbubtw.domain.work_calendar.inline

import com.efikay.wbubtw.shared.utils.DateUtils
import kotlinx.datetime.Month
import java.time.Year

data class WorkCalendarInlineParseResult(
    val year: Int,
    val month: Month
) {
    companion object {
        fun fromQueryString(preparedQueryString: String): WorkCalendarInlineParseResult {
            val today = DateUtils.getToday()

            return WorkCalendarInlineParseResult(
                year = tryParseYear(preparedQueryString) ?: today.year,
                month = tryParseMonth(preparedQueryString) ?: today.month,
            )
        }

        private fun tryParseYear(preparedQueryString: String): Int? {
            val possibleYear = preparedQueryString.filter { it.isDigit() }.toIntOrNull() ?: return null

            return POSSIBLE_YEARS[possibleYear]
        }

        private fun tryParseMonth(preparedQueryString: String): Month? {
            assert(MONTH_PREFIXES.entries.all { (prefix) ->
                prefix.length == MONTH_PREFIX_LEN
            }) { "All month prefixes must have length of $MONTH_PREFIX_LEN!" }

            if (preparedQueryString.length < MONTH_PREFIX_LEN) {
                return null
            }

            val (_, month) = MONTH_PREFIXES.entries.find { (prefix) ->
                preparedQueryString.contains(prefix)
            } ?: return null

            return month
        }
    }
}

private val POSSIBLE_YEARS by lazy {
    val currentYear = Year.now().value
    val yearsWindow = 1

    // 22 -> 2023
    // 24 -> 2024 // <- currentYear
    // 25 -> 2025
    ((currentYear - yearsWindow)..(currentYear + yearsWindow)).fold(mutableMapOf<Int, Int>()) { acc, year ->
        acc[year % 100] = year

        acc
    }
}

private const val MONTH_PREFIX_LEN = 3
private val MONTH_PREFIXES = hashMapOf(
    "янв" to Month.JANUARY,
    "фев" to Month.FEBRUARY,
    "мар" to Month.MARCH,
    "апр" to Month.APRIL,
    "май" to Month.MAY,
    "июн" to Month.JUNE,
    "июл" to Month.JULY,
    "авг" to Month.AUGUST,
    "сен" to Month.SEPTEMBER,
    "окт" to Month.OCTOBER,
    "ноя" to Month.NOVEMBER,
    "дек" to Month.DECEMBER,

    "jan" to Month.JANUARY,
    "feb" to Month.FEBRUARY,
    "mar" to Month.MARCH,
    "apr" to Month.APRIL,
    "may" to Month.MAY,
    "jun" to Month.JUNE,
    "jul" to Month.JULY,
    "aug" to Month.AUGUST,
    "sep" to Month.SEPTEMBER,
    "oct" to Month.OCTOBER,
    "nov" to Month.NOVEMBER,
    "dec" to Month.DECEMBER,
)