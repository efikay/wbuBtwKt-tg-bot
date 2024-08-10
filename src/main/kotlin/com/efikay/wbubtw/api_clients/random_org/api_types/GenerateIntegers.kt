package com.efikay.wbubtw.api_clients.random_org.api_types

data class GenerateIntegersApiParams(
    val apiKey: String,

    /**
     * The lower boundary for the range from which the random numbers will be picked. Must be within the [-1e9,1e9] range.
     */
    val min: Int,

    /**
     * The upper boundary for the range from which the random numbers will be picked. Must be within the [-1e9,1e9] range.
     * */
    val max: Int,

    /**
     * Should all numbers be unique.
     * @default false
     */
    val replacement: Boolean = true,

    /**
     * How many random integers you need. Must be within the [1,1e4] range.
     * */
    val n: Int
)

data class GenerateIntegersApiResponseBody(val data: List<Int>, val completionTime: String)

data class GenerateIntegersApiResponse(val random: GenerateIntegersApiResponseBody)
