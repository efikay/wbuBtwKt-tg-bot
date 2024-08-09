package com.efikay.wbubtw.bot

import com.efikay.wbubtw.bot.challenge_results.ChallengeResultsService
import com.efikay.wbubtw.challenge.ChallengeId
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.UpdateHandler
import eu.vendeli.tgbot.api.answer.answerInlineQuery
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.InlineQueryUpdate
import eu.vendeli.tgbot.types.internal.UpdateType
import org.springframework.stereotype.Service

@Service
class BotService(private val challengeResultsService: ChallengeResultsService) {
    @CommandHandler(["/start"])
    suspend fun start(user: User, bot: TelegramBot) {
        message { "Hello, what's your name?" }.send(user, bot)

        bot.inputListener[user] = "conversation"
    }

    @CommandHandler(["/iq"])
    suspend fun iqCommand(user: User, bot: TelegramBot) {
        message {
            challengeResultsService.getChallengeResultMessage(user, ChallengeId.ICQ)
        }.send(user, bot)
    }

    @CommandHandler(["/asd"])
    suspend fun asdCommand(user: User, bot: TelegramBot) {
        message {
            challengeResultsService.getChallengeResultMessage(user, ChallengeId.ASD)
        }.send(user, bot)
    }

    @CommandHandler(["/bad"])
    suspend fun badCommand(user: User, bot: TelegramBot) {
        message {
            challengeResultsService.getChallengeResultMessage(user, ChallengeId.BAD)
        }.send(user, bot)
    }

    @CommandHandler(["/smesharik"])
    suspend fun smesharikCommand(user: User, bot: TelegramBot) {
        message {
            challengeResultsService.getChallengeResultMessage(user, ChallengeId.KIKORIK)
        }.send(user, bot)
    }

    @UpdateHandler([UpdateType.INLINE_QUERY])
    suspend fun answerInline(update: InlineQueryUpdate, user: User, bot: TelegramBot) {
        val inlineQuery = update.origin.inlineQuery ?: return

        val inlineResults = challengeResultsService.getUserInlineResults(user)

        answerInlineQuery(inlineQuery.id, inlineResults).options {
            isPersonal = true
            cacheTime = 0
        }.send(bot)
    }
}

