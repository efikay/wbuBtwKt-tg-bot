package com.efikay.wbubtw.challenge

import com.efikay.wbubtw.challenge.contest.ChallengeContest
import com.efikay.wbubtw.challenge.contest.RandomQuantityContest
import com.efikay.wbubtw.random.service.RandomService
import org.springframework.stereotype.Service

@Service
class ChallengeService(private val randomService: RandomService) {
    val challenges: List<ChallengeContest> = listOf(
        RandomQuantityContest(
            displayName = "ICQ-challenge",
            getQuantity = { randomService.getRandomNumber(1..200) },
            displayDescription = "Измеряет ICQ точнее менсы (доказано британскими учеными)",
            resultTemplates = hashMapOf(
                1..39 to listOf(
                    "Ну ты и лузер! %d IQ. С таким только в деревене флексить",
                    "Мои соболезнования... %d ICQ"
                ),
                40..79 to listOf("Ну такое... Шо с тебя взять... 🤤 %d IQ"),
                80..99 to listOf("80-99 IQ"),
                100..119 to listOf("100-119 IQ"),
                120..159 to listOf("120-159 IQ"),
                160..200 to listOf("genius! EXCELLENT IQ – %d")
            )
        ),
        RandomQuantityContest(
            displayName = "Тест на аутизм",
            getQuantity = { randomService.getRandomNumber(1..100) },
            displayDescription = "А есть ли смысл проверяться? Ну нажми",
            resultTemplates = hashMapOf(
                1..24 to listOf(
                    "Вы не аутист!",
                    "Ваш уровень аутизма оценен в %d%"
                ),
                25..49 to listOf(
                    "Ты почти не аутист... Тест показал %d%, ну можешь не париться"
                ),
                50..74 to listOf(
                    "Мы не можем точно определить, но у вас примерно 50-74% аутизма. Много ли или мало – решать тебе"
                ),
                75..100 to listOf(
                    "Имеются серьезные основания полагать что... Думайте"
                ),
            )
        )
    )

    fun generateChallengeResults(): List<ChallengeResult> = challenges.map { it.execute() }
}