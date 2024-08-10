package com.efikay.wbubtw.app.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class WbubtwController(private val service: WbubtwService) {
    @GetMapping("rng-status")
    fun getRngStatus() = mapOf(
        "status" to service.getRngStatus()
    )

    @GetMapping("random-number")
    fun getRandomNumber() = mapOf(
        "number" to service.getRandomNumber()
    )
}
