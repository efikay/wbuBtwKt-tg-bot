package com.efikay.wbubtw.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateUtils {
    companion object {
        fun getCurrentMonth(): Month {
            val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            return currentDateTime.month
        }
    }
}