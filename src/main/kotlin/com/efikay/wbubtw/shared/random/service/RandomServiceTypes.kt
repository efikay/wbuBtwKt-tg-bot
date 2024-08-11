package com.efikay.wbubtw.shared.random.service

enum class RandomServiceAliveStatus {
    ALIVE,
    STOPPED,
}

data class RandomServiceUsage(
    val requestsLeft: Int?,
    val bitsLeft: Int?
)

data class RandomServiceInfo(
    val displayName: String,
    val aliveStatus: RandomServiceAliveStatus,
    val preservedNumbersAmount: Int,
    val usage: RandomServiceUsage,
)