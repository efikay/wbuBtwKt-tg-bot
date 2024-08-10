package com.efikay.wbubtw.bot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import kotlinx.datetime.Clock
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller

@Controller
class BotController(private val botService: BotService) {
    private val logger = LoggerFactory.getLogger(BotController::class.java)

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

    @CommandHandler(["/rng"])
    suspend fun rngCommand(user: User, bot: TelegramBot) {
        val rngStatus = botService.getRngStatus()

        message {
            """
                - Какой рандом используется: ${rngStatus.displayName}
                - Сколько запросов еще есть: ${rngStatus.usage.requestsLeft ?: "<No data>"}
                - Сколько бит еще есть: ${rngStatus.usage.bitsLeft ?: "<No data>"}
                - Сколько рандомных чисел припасено локально: ${rngStatus.preservedNumbersAmount}
            """.trimIndent()
        }.send(user, bot)
    }


    @CommandHandler(["/work_sched"])
    suspend fun workScheduleCommand(user: User, bot: TelegramBot) {
        val (days, currentMonth) = botService.getCurrentMonthWorkCalendar()

        val formattedWorkCalendarMonth = BotUtils.formatMonthWorkCalendar(currentMonth, days)
        logger.info(
            formattedWorkCalendarMonth
        )

        message {
            """🗓️ Производственный календарь за текущий месяц
                
$formattedWorkCalendarMonth
            """.trimIndent()
        }.options {
            parseMode = ParseMode.MarkdownV2
        }.send(user, bot)
    }


}

