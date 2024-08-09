package com.efikay.wbubtw.challenge

enum class ChallengeType(val displayName: String, val range: IntRange, val unit: String?) {
    ABUSE("Травля", 1..100, "%"),
    IQ("ICQ", 1..200, null),
    ASD("Аутизм", 1..100, "%"),
}
