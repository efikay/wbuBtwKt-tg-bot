package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.bot.BotCommand
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.UpdateHandler
import eu.vendeli.tgbot.api.message.editText
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.CallbackQueryUpdate
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import eu.vendeli.tgbot.types.internal.UpdateType
import org.springframework.stereotype.Controller

@Controller
class WorkCalendarBotController(
    private val service: WorkCalendarBotService
) {
    @CommandHandler([BotCommand.WORK_AGENDA])
    suspend fun workScheduleCommand(update: ProcessedUpdate, user: User, bot: TelegramBot) {
        val (
            formattedCalendar,
            choices,
        ) = service.getCurrentMonthWorkCalendar()

        val (prevChoice, nextChoice) = choices

        message {
            """🗓️ Производственный календарь за текущий месяц
                
$formattedCalendar
            """.trimIndent()
        }.options {
            parseMode = ParseMode.MarkdownV2
        }.inlineKeyboardMarkup {
            prevChoice.text callback prevChoice.callbackQueryData
            nextChoice.text callback nextChoice.callbackQueryData
        }.send(user, bot)
    }

    @UpdateHandler([UpdateType.CALLBACK_QUERY])
    suspend fun onMonthPicked(update: CallbackQueryUpdate, user: User, bot: TelegramBot) {
        val callbackData = update.origin.callbackQuery?.data ?: return
        val messageIdToEdit = update.callbackQuery.message?.messageId ?: return

        val (year, month) = WorkCalendarBotInlineKeyboardChoice.decodeYearMonthOrNull(callbackData) ?: return
        val (
            formattedCalendar,
            choices,
        ) = service.getMonthWorkCalendar(month, year)

        val (prevChoice, nextChoice) = choices

        editText(messageIdToEdit) {
            """🗓️ Производственный календарь за $year $month
                
$formattedCalendar
            """.trimIndent()
        }.options {
            parseMode = ParseMode.MarkdownV2
        }.inlineKeyboardMarkup {
            prevChoice.text callback prevChoice.callbackQueryData
            nextChoice.text callback nextChoice.callbackQueryData
        }.send(user, bot)
    }
}