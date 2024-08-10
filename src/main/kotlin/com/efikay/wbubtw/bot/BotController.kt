package com.efikay.wbubtw.bot

import com.efikay.wbubtw.challenge.ChallengeId
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.UpdateHandler
import eu.vendeli.tgbot.api.answer.answerInlineQuery
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.InlineQueryUpdate
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import eu.vendeli.tgbot.types.internal.UpdateType
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

    @CommandHandler(["/iq"])
    suspend fun iqCommand(user: User, bot: TelegramBot) {
        message { botService.getUserChallengeResultMessage(user, ChallengeId.ICQ) }.send(user, bot)
    }

    @CommandHandler(["/asd"])
    suspend fun asdCommand(user: User, bot: TelegramBot) {
        message { botService.getUserChallengeResultMessage(user, ChallengeId.ASD) }.send(user, bot)
    }

    @CommandHandler(["/bad"])
    suspend fun badCommand(user: User, bot: TelegramBot) {
        message { botService.getUserChallengeResultMessage(user, ChallengeId.BAD) }.send(user, bot)
    }

    @CommandHandler(["/smesharik"])
    suspend fun smesharikCommand(user: User, bot: TelegramBot) {
        message { botService.getUserChallengeResultMessage(user, ChallengeId.KIKORIK) }.send(user, bot)
    }

    @CommandHandler(["/big_o"])
    suspend fun bigOCommand(user: User, bot: TelegramBot) {
        message { botService.getUserChallengeResultMessage(user, ChallengeId.BIG_O) }.send(user, bot)
    }

    @CommandHandler(["/all"])
    suspend fun allCommand(user: User, bot: TelegramBot) {
        message {
            """Ваш диагноз по всем испытаниям:
                |
                |${botService.getAllUserChallengeResultMessages(user).joinToString("\n\n")}
            """.trimMargin()
        }.send(user, bot)
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

    @CommandHandler(["/users"])
    suspend fun usersCommand(user: User, bot: TelegramBot) {
        val stats = botService.getUsersChallengeStats()

        message {
            """Статистика по пользователям
                
                |Тесты:
                |
                |${
                stats.joinToString("\n\n") {
                    """${it.displayChallengeName}:
                  |- Всего участников: ${it.amountUsersChallenged}
                  |- Средний результат: ${it.displayAverageValue}
                  |- Макс. результат: ${it.displayMaxValue}
              """.trimIndent()
                }
            }
            """.trimIndent().trimMargin()
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

    @UpdateHandler([UpdateType.INLINE_QUERY])
    suspend fun answerInline(update: InlineQueryUpdate, user: User, bot: TelegramBot) {
        val inlineQuery = update.origin.inlineQuery ?: return

        val inlineResults = botService.getUserInlineResults(user).toMutableList()
        inlineResults.add(botService.getUserTotalInlineResult(user))

        answerInlineQuery(inlineQuery.id, inlineResults).options {
            isPersonal = true
            cacheTime = 0
        }.send(bot)
    }
}

