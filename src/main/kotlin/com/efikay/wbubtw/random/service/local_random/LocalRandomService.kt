package com.efikay.wbubtw.random.service.local_random

import com.efikay.wbubtw.app.config.AppConfig
import com.efikay.wbubtw.random.service.RandomService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile(AppConfig.PROFILE_DEV)
class LocalRandomService(private val min: Int = 0, private val max: Int = 100) : RandomService {
    override fun getRandomNumbers(amount: UInt) = (1u..amount).map {
        getRandomNumber()
    }

    private fun getRandomNumber(): Int = ((Math.random() * ((max - min) + 1)) + min).toInt()
}
