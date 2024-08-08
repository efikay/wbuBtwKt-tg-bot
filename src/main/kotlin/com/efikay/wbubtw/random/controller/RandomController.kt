package com.efikay.wbubtw.random.controller

import com.efikay.wbubtw.random.service.RandomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/random")
class RandomController(private val randomService: RandomService) {
    @GetMapping("numbers")
    fun getRandomNumbers(): String {
        return "Random numbers: ${this.randomService.getRandomNumbers(3u)}"
    }
}
