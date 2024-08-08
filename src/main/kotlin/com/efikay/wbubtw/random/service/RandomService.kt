package com.efikay.wbubtw.random.service

interface RandomService {
    fun getRandomNumbers(amount: UInt): List<Int>
}