package com.efikay.wbubtw.domain.work_calendar.inline

import com.efikay.wbubtw.bot.BotCommand
import com.efikay.wbubtw.shared.utils.DateUtils
import com.efikay.wbubtw.shared.utils.extensions.toRussianString
import com.efikay.wbubtw.domain.work_calendar.bot.WorkCalendarBotService
import eu.vendeli.tgbot.types.ParseMode
import eu.vendeli.tgbot.types.Text
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import org.springframework.stereotype.Service
import java.time.Year

@Service
class WorkCalendarInlineResultService(
    // FIXME: cross-import from ../bot. Reorganize logically
    private val botService: WorkCalendarBotService
) {
    fun generateInlineResult(queryString: String): InlineQueryResult {
        val preparedQueryString = queryString.trim()

        if (preparedQueryString.isEmpty()) {
            return generateTodayInlineResult()
        }

        val (year, month) = WorkCalendarInlineParseResult.fromQueryString(preparedQueryString)

        val id = "wc"
        val title = "Производственный календарь"
        val description = "Производственный календарь за $year ${month.toRussianString()}"

        val (formattedCalendar) = botService.getMonthWorkCalendar(month, Year.of(year))

        return InlineQueryResult.Article(
            id = id,
            title = title,
            description = description,
            inputMessageContent = Text(
                """🗓️ Производственный календарь за $year ${month.toRussianString()}
                
$formattedCalendar

ℹ️ _Больше опций календаря – в ЛС ${BotCommand.WORK_AGENDA}_
            """.trimIndent(),
                parseMode = ParseMode.MarkdownV2,
            ),
        )
    }

    fun generateTodayInlineResult(): InlineQueryResult {
        val id = "wc"
        val title = "Производственный календарь"
        val description = "Совет: можешь указать интересующие тебя месяц и год"

        val currentMonth = DateUtils.getToday().month

        val (formattedCalendar) = botService.getMonthWorkCalendar(currentMonth, Year.now())

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