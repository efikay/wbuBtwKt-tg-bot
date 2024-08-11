package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.bot.BotCommand
import com.efikay.wbubtw.utils.DateUtils
import com.efikay.wbubtw.utils.extensions.toRussianString
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
import java.time.Year

@Controller
class WorkCalendarBotController(
    private val service: WorkCalendarBotService
) {
    @CommandHandler([BotCommand.WORK_AGENDA])
    suspend fun workScheduleCommand(update: ProcessedUpdate, user: User, bot: TelegramBot) {
        val today = DateUtils.getToday()

        val (
            formattedCalendar,
            choices,
        ) = service.getMonthWorkCalendar(
            year = Year.of(today.year),
            month = today.month,
        )

        val (prevChoice, nextChoice) = choices

        val calendarTips = WorkCalendarBotUtils.formatCalendarTips(Year.now())

        message {
            """🗓️ Производственный календарь за текущий месяц
                
$formattedCalendar

$calendarTips
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

        val calendarTips = WorkCalendarBotUtils.formatCalendarTips(year)

        editText(messageIdToEdit) {
            """🗓️ Производственный календарь за $year ${month.toRussianString()}
                
$formattedCalendar

$calendarTips
            """.trimIndent()
        }.options {
            parseMode = ParseMode.MarkdownV2
        }.inlineKeyboardMarkup {
            prevChoice.text callback prevChoice.callbackQueryData
            nextChoice.text callback nextChoice.callbackQueryData
        }.send(user, bot)
    }
}