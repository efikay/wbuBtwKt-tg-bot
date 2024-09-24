package com.efikay.wbubtw.domain.challenge.bot

import com.efikay.wbubtw.domain.challenge.ChallengeId
import com.efikay.wbubtw.domain.challenge.bot.challenge_results.ChallengeResultsService
import com.efikay.wbubtw.domain.challenge.bot.challenge_results.ChallengeTotalResultsService
import eu.vendeli.tgbot.types.User
import org.springframework.stereotype.Service

@Service
class ChallengeBotService(
    private val challengeResultsService: ChallengeResultsService,
    private val challengeTotalResultsService: ChallengeTotalResultsService,
) {
    fun getUsersChallengeStats() = challengeTotalResultsService.getUsersChallengeTotalStats()

    fun getUserChallengeResultMessage(user: User, challengeId: ChallengeId): String =
        challengeResultsService.getUserChallengeResult(user.id, challengeId).getDisplayResult()
}