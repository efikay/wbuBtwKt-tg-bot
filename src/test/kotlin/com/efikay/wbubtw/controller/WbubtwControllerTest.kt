package com.efikay.wbubtw.controller

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("main")
class ExampleControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return hello world`() {
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/hello-world"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        "Random number is 42" shouldBe result.response.contentAsString
    }
}