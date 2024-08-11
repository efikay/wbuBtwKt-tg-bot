package com.efikay.wbubtw.utils.extensions

import kotlinx.datetime.Month

val russianMonths = listOf(
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