package com.efikay.wbubtw.bot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import kotlinx.datetime.Clock
import org.springframework.stereotype.Controller

@Controller
class BotController {
    @CommandHandler(["/start"])
    suspend fun start(user: User, bot: TelegramBot) {
        message { "Hello, what's your name?" }.send(user, bot)

        bot.inputListener[user] = "conversation"
    }

    @CommandHandler(["/ping"])
    suspend fun pingCommand(update: ProcessedUpdate, user: User, bot: TelegramBot) {
        val messageMsTime = update.origin.message?.date?.toEpochMilliseconds() ?: return
        val nowMsTime = Clock.System.now().toEpochMilliseconds()

        val reactionTimeMs = nowMsTime - messageMsTime

        message { "Pong 🏓! Время реакции – $reactionTimeMs ms" }.send(user, bot)
    }
}

