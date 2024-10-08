package com.efikay.wbubtw.shared.utils.extensions

import kotlinx.datetime.Month

private val russianMonths = listOf(
    "Январь",
    "Ферваль",
    "Март",
    "Апрель",
    "Май",
    "Июнь",
    "Июль",
    "Август",
    "Сентябрь",
    "Октябрь",
    "Ноябрь",
    "Декабрь",
)


fun Month.toRussianString(): String {
    val monthIndex = this.ordinal

    return russianMonths[monthIndex]
}