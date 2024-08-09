package com.efikay.wbubtw.bot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import org.springframework.stereotype.Service

@Service
class BotService {
    @CommandHandler(["/start"])
    suspend fun start(user: User, bot: TelegramBot) {
        message { "Hello, what's your name?" }.send(user, bot)

        bot.inputListener[user] = "conversation"
    }
}

