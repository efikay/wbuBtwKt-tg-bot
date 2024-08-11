package com.efikay.wbubtw.challenge.inline_result

import com.efikay.wbubtw.challenge.bot.challenge_results.ChallengeResultsService
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service

@Service
class ChallengeInlineResultsService(
    private val challengeResultsService: ChallengeResultsService,
) {
    fun getUserTotalInlineResult(user: User): InlineQueryResult {
        val displayName = if (user.username != null) "@${user.username}" else user.firstName

        val message =
            challengeResultsService.getUserChallengeResults(user.id).fold(mutableListOf<String>()) { acc, it ->
                acc.add(
                    String.format(
                        """====(%s)====:%n%s""",
                        it.displayName,
                        it.getDisplayResult(),
                    )
                )

                acc
            }.joinToString("\n\n")

        return InlineQueryResult.Article(
            id = "all",
            title = "Все за один раз",
            description = "Все тесты",
            inputMessageContent = Text(
                """
                Результаты всех тестов для юзера $displayName:
                |
                |${message}
            """.trimMargin()
            )
        )
    }

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