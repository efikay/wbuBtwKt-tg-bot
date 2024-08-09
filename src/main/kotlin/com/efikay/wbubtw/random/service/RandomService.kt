package com.efikay.wbubtw.random.service

interface RandomService {
    fun getRandomNumbers(amount: Int, range: IntRange): List<Int>
    fun getRandomNumber(range: IntRange): Int

    fun getInfo(): RandomServiceInfo
}