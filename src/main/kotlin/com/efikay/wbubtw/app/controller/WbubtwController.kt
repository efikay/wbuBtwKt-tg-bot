package com.efikay.wbubtw.app.controller

import com.efikay.wbubtw.random.service.RandomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class WbubtwController(private val randomService: RandomService) {

    @GetMapping("hello-world")
    fun helloWorld(): String {
        val randomNumber = this.randomService.getRandomNumbers(1, 1..100)

        return "Random number is $randomNumber"
    };
}
