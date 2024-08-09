package com.efikay.wbubtw.bot

import com.efikay.wbubtw.bot.challenge_results.ChallengeResultsService
import com.efikay.wbubtw.challenge.ChallengeId
import com.efikay.wbubtw.random.service.RandomService
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service

@Service
class BotService(
    private val challengeResultsService: ChallengeResultsService,
    private val randomService: RandomService,
) {
    fun getRngStatus() = randomService.getInfo()

    fun getChallengeResultMessage(user: User, challengeId: ChallengeId): String =
        challengeResultsService.getUserChallengeResult(user.id, challengeId).getDisplayResult()

    fun getUserInlineResults(user: User): List<InlineQueryResult> =
        challengeResultsService.getUserChallengeResults(user.id).map {
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
}