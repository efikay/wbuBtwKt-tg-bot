package com.efikay.wbubtw.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateUtils {
    companion object {
        fun getCurrentMonth() = getToday().month

        fun getToday() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}