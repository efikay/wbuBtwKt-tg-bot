package com.efikay.wbubtw.random.service.random_org.api_types

data class GetUsageParams(
    val apiKey: String,
)

data class GetUsageResponse(
    val status: String,
    val creationTime: String,
    val bitsLeft: Int,
    val requestsLeft: Int,
    val totalBits: Int,
    val totalRequests: Int,
)
