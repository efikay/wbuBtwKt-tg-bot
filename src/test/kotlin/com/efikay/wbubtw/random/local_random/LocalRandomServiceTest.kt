package com.efikay.wbubtw.random.local_random

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocalRandomServiceTest {
    private lateinit var service: LocalRandomService

    @BeforeEach
    fun init() {
        this.service = LocalRandomService()
    }


    @Test
    fun getRandomNumber() {
        val result = service.getRandomNumber()

        result shouldBe 42
    }

    @Test
    fun getRandomNumbers() {
    }
}