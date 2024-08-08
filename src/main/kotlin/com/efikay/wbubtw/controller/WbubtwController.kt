package com.efikay.wbubtw.controller

import com.efikay.wbubtw.random.RandomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class WbubtwController(private val randomService: RandomService) {

    @GetMapping("hello-world")
    fun helloWorld(): String {
        val randomNumber = this.randomService.getRandomNumber()

        return "Random number is $randomNumber"
    };
}
