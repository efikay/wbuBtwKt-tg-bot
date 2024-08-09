package com.efikay.wbubtw.bot

import com.efikay.wbubtw.bot.inline_options.BotInlineResultsService
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
class BotService(private val inlineResultsService: BotInlineResultsService) {
    @CommandHandler(["/start"])
    suspend fun start(user: User, bot: TelegramBot) {
        message { "Hello, what's your name?" }.send(user, bot)

        bot.inputListener[user] = "conversation"
    }

    @UpdateHandler([UpdateType.INLINE_QUERY])
    suspend fun answerInline(update: InlineQueryUpdate, user: User, bot: TelegramBot) {
        val inlineQuery = update.origin.inlineQuery ?: return

        val inlineResults = inlineResultsService.getUserInlineResults(user.id)

        answerInlineQuery(inlineQuery.id, inlineResults).options {
            isPersonal = true
            cacheTime = 5_000
        }.send(bot)
    }
}

