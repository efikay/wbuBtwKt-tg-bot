package com.efikay.wbubtw.random.bot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import org.springframework.stereotype.Controller

@Controller
class RandomBotController(
    private val service: RandomBotService
) {
    @CommandHandler(["/rng"])
    suspend fun rngCommand(user: User, bot: TelegramBot) {
        val rngStatus = service.getRngStatus()

        message {
            """
                - Какой рандом используется: ${rngStatus.displayName}
                - Сколько запросов еще есть: ${rngStatus.usage.requestsLeft ?: "<No data>"}
                - Сколько бит еще есть: ${rngStatus.usage.bitsLeft ?: "<No data>"}
                - Сколько рандомных чисел припасено локально: ${rngStatus.preservedNumbersAmount}
            """.trimIndent()
        }.send(user, bot)
    }
}