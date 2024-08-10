package com.efikay.wbubtw.random.bot

import com.efikay.wbubtw.random.service.RandomService
import org.springframework.stereotype.Service

@Service
class RandomBotService(
    private val randomService: RandomService,
) {
    fun getRngStatus() = randomService.getInfo()
}