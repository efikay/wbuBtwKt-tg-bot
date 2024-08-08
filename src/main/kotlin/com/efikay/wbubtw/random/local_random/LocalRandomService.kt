package com.efikay.wbubtw.random.local_random

import com.efikay.wbubtw.config.AppConfig
import com.efikay.wbubtw.random.RandomService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile(AppConfig.PROFILE_DEV)
class LocalRandomService: RandomService {
    override fun getRandomNumber(): Int {
        return 42
    }

    override fun getRandomNumbers(amount: Int): IntArray {
        TODO("Not yet implemented")
    }
}
