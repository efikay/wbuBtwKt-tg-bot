package com.efikay.wbubtw.work_calendar.bot

import com.efikay.wbubtw.bot.BotCommand
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service

@Service
class WorkCalendarInlineResultService(
    private val botService: WorkCalendarBotService
) {
    fun generateInlineResult(): InlineQueryResult {
        val id = "wc"
        val title = "Производственный календарь"
        val description = "Узнай свои рабочие и нерабочие дни всего в один клик"

        val (formattedCalendar) = botService.getCurrentMonthWorkCalendar()

        return InlineQueryResult.Article(
            id = id,
            title = title,
            description = description,
            inputMessageContent = Text(
                """🗓️ Производственный календарь на текущий месяц
                
$formattedCalendar

ℹ️ _Больше опций календаря – в ЛС ${BotCommand.WORK_AGENDA}_
            """.trimIndent(),
                parseMode = ParseMode.MarkdownV2,
            ),
        )
    }
}