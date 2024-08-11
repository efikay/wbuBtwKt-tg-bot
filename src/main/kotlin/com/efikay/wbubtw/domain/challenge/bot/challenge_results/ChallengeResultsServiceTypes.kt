package com.efikay.wbubtw.domain.challenge.bot.challenge_results

data class UsersChallengeTotalStats(
    var displayChallengeName: String,
    val amountUsersChallenged: Int,
    val displayAverageValue: String,
    val displayMaxValue: String,
)