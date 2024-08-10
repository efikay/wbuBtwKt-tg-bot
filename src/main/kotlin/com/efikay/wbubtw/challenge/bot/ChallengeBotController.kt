package com.efikay.wbubtw.challenge.bot

import com.efikay.wbubtw.challenge.ChallengeId

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.UpdateHandler
import eu.vendeli.tgbot.api.answer.answerInlineQuery
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.InlineQueryUpdate
import eu.vendeli.tgbot.types.internal.UpdateType
import org.springframework.stereotype.Controller

@Controller
class ChallengeBotController(
    private val service: ChallengeBotService
) {
    @CommandHandler(["/iq"])
    suspend fun iqCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.ICQ) }.send(user, bot)
    }

    @CommandHandler(["/asd"])
    suspend fun asdCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.ASD) }.send(user, bot)
    }

    @CommandHandler(["/bad"])
    suspend fun badCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.BAD) }.send(user, bot)
    }

    @CommandHandler(["/smesharik"])
    suspend fun smesharikCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.KIKORIK) }.send(user, bot)
    }

    @CommandHandler(["/big_o"])
    suspend fun bigOCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.BIG_O) }.send(user, bot)
    }

    @CommandHandler(["/all"])
    suspend fun allCommand(user: User, bot: TelegramBot) {
        message {
            """Ваш диагноз по всем испытаниям:
                |
                |${service.getAllUserChallengeResultMessages(user).joinToString("\n\n")}
            """.trimMargin()
        }.send(user, bot)
    }


    @CommandHandler(["/users"])
    suspend fun usersCommand(user: User, bot: TelegramBot) {
        val stats = service.getUsersChallengeStats()

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

    @UpdateHandler([UpdateType.INLINE_QUERY])
    suspend fun answerInline(update: InlineQueryUpdate, user: User, bot: TelegramBot) {
        val inlineQuery = update.origin.inlineQuery ?: return

        val inlineResults = service.getUserInlineResults(user).toMutableList()
        inlineResults.add(service.getUserTotalInlineResult(user))

        answerInlineQuery(inlineQuery.id, inlineResults).options {
            isPersonal = true
            cacheTime = 0
        }.send(bot)
    }
}
