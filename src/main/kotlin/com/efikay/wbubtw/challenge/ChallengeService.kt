package com.efikay.wbubtw.challenge

import com.efikay.wbubtw.random.service.RandomService
import org.springframework.stereotype.Service

@Service
class ChallengeService(private val randomService: RandomService) {
    fun generateChallengeResults() = ChallengeType.entries.map {
        val result = randomService.getRandomNumber(it.range)

        ChallengeResult(it, result)
    }
}