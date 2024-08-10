package com.efikay.wbubtw.app.controller

import com.efikay.wbubtw.random.service.RandomService
import org.springframework.stereotype.Service

@Service
class WbubtwService(
    private val randomService: RandomService
) {
    fun getRandomNumber() = randomService.getRandomNumber(1..100)

    fun getRngStatus() = randomService.getInfo()
}