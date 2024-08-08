package com.efikay.wbubtw.random

interface RandomService {
    fun getRandomNumber(): Int
    fun getRandomNumbers(amount: Int): IntArray
}