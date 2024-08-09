package com.efikay.wbubtw.bot.inline_options

import com.efikay.wbubtw.challenge.ChallengeResult
import com.efikay.wbubtw.challenge.ChallengeService
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service

@Service
class BotInlineResultsService(private val challengesService: ChallengeService) {
    // FIXME: It's not inline result but just result (refactor structure)
    // TODO: Make commands for /iq, /asd, ...
    val challengeResults = HashMap<Long, List<ChallengeResult>>()

    fun getUserInlineResults(userId: Long): List<InlineQueryResult> {
        return getUserChallengeResults(userId).map {
            val message =
                String.format("Результаты по тесту \"%s\": %d %s", it.type.displayName, it.value, it.type.unit ?: "")

            InlineQueryResult.Article(
                id = it.type.name,
                title = it.type.displayName,
                inputMessageContent = Text(message)
            )
        }
    }

    private fun getUserChallengeResults(userId: Long): List<ChallengeResult> {
        if (challengeResults[userId] == null) {
            val userResults = challengesService.generateChallengeResults()

            challengeResults[userId] = userResults
        }

        return challengeResults[userId]!!
    }
}