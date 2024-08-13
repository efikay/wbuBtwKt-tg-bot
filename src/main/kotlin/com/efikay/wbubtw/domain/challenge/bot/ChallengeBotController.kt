package com.efikay.wbubtw.domain.challenge.bot

import com.efikay.wbubtw.bot.BotCommand
import com.efikay.wbubtw.domain.challenge.ChallengeId
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Controller

@Controller
@ConditionalOnExpression("\${bot.challenges.enabled}") // FIXME: No bean exception if disabled. But it works
class ChallengeBotController(
    private val service: ChallengeBotService,
) {
    @CommandHandler([BotCommand.CHALLENGE_IQ])
    suspend fun iqCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.ICQ) }.send(user, bot)
    }

    @CommandHandler([BotCommand.CHALLENGE_ASD])
    suspend fun asdCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.ASD) }.send(user, bot)
    }

    @CommandHandler([BotCommand.CHALLENGE_BAD])
    suspend fun badCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.BAD) }.send(user, bot)
    }

    @CommandHandler([BotCommand.CHALLENGE_SMESHARIK])
    suspend fun smesharikCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.KIKORIK) }.send(user, bot)
    }

    @CommandHandler([BotCommand.CHALLENGE_BIG_O])
    suspend fun bigOCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.BIG_O) }.send(user, bot)
    }

    @CommandHandler([BotCommand.CHALLENGE_IT])
    suspend fun itCommand(user: User, bot: TelegramBot) {
        message { service.getUserChallengeResultMessage(user, ChallengeId.IT) }.send(user, bot)
    }

    @CommandHandler([BotCommand.ALL_CHALLENGES])
    suspend fun allCommand(user: User, bot: TelegramBot) {
        message {
            """Ваш диагноз по всем испытаниям:
                |
                |${service.getAllUserChallengeResultMessages(user).joinToString("\n\n")}
            """.trimMargin()
        }.send(user, bot)
    }


    @CommandHandler([BotCommand.CHALLENGE_USERS])
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
}
