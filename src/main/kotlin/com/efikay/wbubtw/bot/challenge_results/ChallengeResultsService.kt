package com.efikay.wbubtw.bot.challenge_results

import com.efikay.wbubtw.challenge.ChallengeId
import com.efikay.wbubtw.challenge.ChallengeResult
import com.efikay.wbubtw.challenge.ChallengeService
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service

@Service
class ChallengeResultsService(private val challengesService: ChallengeService) {
    val challengeResults = HashMap<Long, List<ChallengeResult>>()

    fun getChallengeResultMessage(user: User, challengeId: ChallengeId): String =
        getUserChallengeResults(user.id).find { it.challengeId == challengeId }!!.getDisplayResult()

    fun getUserInlineResults(user: User): List<InlineQueryResult> = getUserChallengeResults(user.id).map {
        val displayName = if (user.username != null) "@${user.username}" else user.firstName

        val message =
            String.format(
                """Результаты для $displayName по тесту "%s":%n%n%s""",
                it.displayName,
                it.getDisplayResult(),
            )

        InlineQueryResult.Article(
            id = it.displayName,
            title = it.displayName,
            description = it.displayDescription,
            inputMessageContent = Text(message)
        )
    }

    private fun getUserChallengeResults(userId: Long): List<ChallengeResult> {
        if (challengeResults[userId] == null) {
            val userResults = challengesService.generateChallengeResults()

            challengeResults[userId] = userResults
        }

        return challengeResults[userId]!!
    }
}