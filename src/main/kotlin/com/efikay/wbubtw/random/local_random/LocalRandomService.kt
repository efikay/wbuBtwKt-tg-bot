package com.efikay.wbubtw.random.local_random

import com.efikay.wbubtw.config.AppConfig
import com.efikay.wbubtw.random.RandomService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile(AppConfig.PROFILE_DEV)
class LocalRandomService(private val min: Int = 0, private val max: Int = 100) : RandomService {

    override fun getRandomNumber(): Int = ((Math.random() * ((max - min) + 1)) + min).toInt()

    override fun getRandomNumbers(amount: Int) = IntArray(amount) {
        getRandomNumber()
    }
}
