package com.efikay.wbubtw.shared.random.bot

import com.efikay.wbubtw.shared.random.service.RandomService
import org.springframework.stereotype.Service

@Service
class RandomBotService(
    private val randomService: RandomService,
) {
    fun getRngStatus() = randomService.getInfo()
}