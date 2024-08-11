package com.efikay.wbubtw.bot

import com.efikay.wbubtw.domain.challenge.inline_result.ChallengeInlineResultsService
import com.efikay.wbubtw.domain.work_calendar.inline.WorkCalendarInlineResultService
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.UpdateHandler
import eu.vendeli.tgbot.api.answer.answerInlineQuery
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.InlineQueryUpdate
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import eu.vendeli.tgbot.types.internal.UpdateType
import kotlinx.datetime.Clock
import org.springframework.stereotype.Controller

@Controller
class BotController(
    private val challengeInlineResultsService: ChallengeInlineResultsService,
    private val workCalendarInlineResultService: WorkCalendarInlineResultService
) {
    @CommandHandler([BotCommand.START])
    suspend fun start(user: User, bot: TelegramBot) {
        message { "Hello, what's your name?" }.send(user, bot)

        bot.inputListener[user] = "conversation"
    }

    @CommandHandler([BotCommand.PING])
    suspend fun pingCommand(update: ProcessedUpdate, user: User, bot: TelegramBot) {
        val messageMsTime = update.origin.message?.date?.toEpochMilliseconds() ?: return
        val nowMsTime = Clock.System.now().toEpochMilliseconds()

        val reactionTimeMs = nowMsTime - messageMsTime

        message { "Pong 🏓! Время реакции – $reactionTimeMs ms" }.send(user, bot)
    }

    @UpdateHandler([UpdateType.INLINE_QUERY])
    suspend fun answerInline(update: InlineQueryUpdate, user: User, bot: TelegramBot) {
        val inlineQuery = update.origin.inlineQuery ?: return

        val inlineResults =
            challengeInlineResultsService.getAvailableInlineResults(user) +
                    workCalendarInlineResultService.generateInlineResult(inlineQuery.query)

        answerInlineQuery(inlineQuery.id, inlineResults).options {
            isPersonal = true
            cacheTime = 0
        }.send(bot)
    }
}

