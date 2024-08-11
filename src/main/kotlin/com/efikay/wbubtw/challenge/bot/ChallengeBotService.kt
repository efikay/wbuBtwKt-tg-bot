package com.efikay.wbubtw.challenge.bot

import com.efikay.wbubtw.challenge.ChallengeId
import com.efikay.wbubtw.challenge.bot.challenge_results.ChallengeResultsService
import com.efikay.wbubtw.challenge.bot.challenge_results.ChallengeTotalResultsService
import eu.vendeli.tgbot.types.User
import org.springframework.stereotype.Service

@Service
class ChallengeBotService(
    private val challengeResultsService: ChallengeResultsService,
    private val challengeTotalResultsService: ChallengeTotalResultsService,
) {
    fun getUsersChallengeStats() = challengeTotalResultsService.getUsersChallengeTotalStats()

    fun getAllUserChallengeResultMessages(user: User) = ChallengeId.entries.map {
        val resultMessage = getUserChallengeResultMessage(user, it)

        """====($it)====
            |$resultMessage""".trimMargin()
    }

    fun getUserChallengeResultMessage(user: User, challengeId: ChallengeId): String =
        challengeResultsService.getUserChallengeResult(user.id, challengeId).getDisplayResult()
}