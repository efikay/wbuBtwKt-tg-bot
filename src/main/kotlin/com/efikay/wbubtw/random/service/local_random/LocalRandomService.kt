package com.efikay.wbubtw.random.service.local_random

import com.efikay.wbubtw.app.config.AppConfig
import com.efikay.wbubtw.random.service.RandomService
import com.efikay.wbubtw.random.service.RandomServiceAliveStatus
import com.efikay.wbubtw.random.service.RandomServiceInfo
import com.efikay.wbubtw.random.service.RandomServiceUsage
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile(AppConfig.PROFILE_DEV)
class LocalRandomService : RandomService {
    override fun getRandomNumbers(amount: Int, range: IntRange) = (1..amount).map {
        getRandomNumber(range)
    }

    override fun getRandomNumber(range: IntRange): Int {
        assert(!range.isEmpty()) { "Cannot get random number of empty range!" }

        return ((Math.random() * ((range.maxOrNull()!! - range.minOrNull()!!) + 1)) + range.minOrNull()!!).toInt()
    }

    override fun getInfo() = RandomServiceInfo(
        displayName = "🧲 Local random",
        aliveStatus = RandomServiceAliveStatus.ALIVE,
        preservedNumbersAmount = 0,
        usage = RandomServiceUsage(
            requestsLeft = null,
            bitsLeft = null,
        )
    )
}
