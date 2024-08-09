package com.efikay.wbubtw.challenge

enum class ChallengeType(
    val displayName: String,
    val description: String,
    val range: IntRange,
    val unit: String?
) {
    ABUSE("Травля", "Насколько ты сегодня токс", 1..100, "%"),
    IQ("ICQ", "Твой IQ-level", 1..200, null),
    ASD("Аутизм", "Твой уровень аутизма", 1..100, "%"),
    KIKORIK("Смешарик", "Какой ты сегодня смешарик? (и смешарик ли вообще)", 1..10, "кар-карычей из 10")
}
