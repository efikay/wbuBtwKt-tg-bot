package com.efikay.wbubtw.work_calendar.bot

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.User
import org.springframework.stereotype.Controller

@Controller
class WorkCalendarBotController(
    private val service: WorkCalendarBotService
) {
    @CommandHandler(["/work_sched"])
    suspend fun workScheduleCommand(user: User, bot: TelegramBot) {
        val (days, currentMonth) = service.getCurrentMonthWorkCalendar()

        val formattedWorkCalendarMonth = WorkCalendarBotUtils.formatMonthWorkCalendar(currentMonth, days)

        message {
            """🗓️ Производственный календарь за текущий месяц
                
$formattedWorkCalendarMonth
            """.trimIndent()
        }.options {
            parseMode = ParseMode.MarkdownV2
        }.send(user, bot)
    }
}