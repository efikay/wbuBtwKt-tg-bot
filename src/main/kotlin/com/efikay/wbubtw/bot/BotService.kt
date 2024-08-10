package com.efikay.wbubtw.bot

import com.efikay.wbubtw.bot.challenge_results.ChallengeResultsService
import com.efikay.wbubtw.bot.challenge_results.ChallengeTotalResultsService
import com.efikay.wbubtw.challenge.ChallengeId
import com.efikay.wbubtw.random.service.RandomService
import com.efikay.wbubtw.utils.DateUtils
import com.efikay.wbubtw.work_calendar.WorkCalendarService
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service

@Service
class BotService(
    private val challengeResultsService: ChallengeResultsService,
    private val challengeTotalResultsService: ChallengeTotalResultsService,
    private val randomService: RandomService,
    private val workCalendarService: WorkCalendarService,
) {
    fun getRngStatus() = randomService.getInfo()

    fun getUsersChallengeStats() = challengeTotalResultsService.getUsersChallengeTotalStats()

    fun getAllUserChallengeResultMessages(user: User) = ChallengeId.entries.map {
        val resultMessage = getUserChallengeResultMessage(user, it)

        """====($it)====
            |$resultMessage""".trimMargin()
    }

    fun getUserChallengeResultMessage(user: User, challengeId: ChallengeId): String =
        challengeResultsService.getUserChallengeResult(user.id, challengeId).getDisplayResult()

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

    fun getCurrentMonthWorkCalendar(): GetCurrentMonthWorkCalendarResponse {
        val currentMonth = DateUtils.getCurrentMonth()

        return GetCurrentMonthWorkCalendarResponse(
            days = workCalendarService.getMonthDays(currentMonth),
            currentMonth = currentMonth,
        )
    }
}