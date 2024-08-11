package com.efikay.wbubtw.random.local_random

import com.efikay.wbubtw.shared.random.service.local_random.LocalRandomService
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
    fun getRandomNumbers() {
        val result = service.getRandomNumbers(3, 1..100)

        result shouldBe 42
    }
}