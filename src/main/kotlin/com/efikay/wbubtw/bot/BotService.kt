package com.efikay.wbubtw.bot

import com.efikay.wbubtw.random.service.RandomService
import org.springframework.stereotype.Service

@Service
class BotService(
    private val randomService: RandomService,
) {
    fun getRngStatus() = randomService.getInfo()
}