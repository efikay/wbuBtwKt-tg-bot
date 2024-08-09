package com.efikay.wbubtw.random.service.random_org

import com.efikay.wbubtw.app.config.AppConfig
import com.efikay.wbubtw.random.service.RandomService
import com.efikay.wbubtw.random.service.random_org.api_types.GenerateIntegersApiParams
import com.efikay.wbubtw.random.service.random_org.api_types.GenerateIntegersApiResponse
import com.efikay.wbubtw.random.service.random_org.api_types.GetUsageApiParams
import com.efikay.wbubtw.random.service.random_org.api_types.GetUsageApiResponse
import com.efikay.wbubtw.random.service.random_org.json_rpc.JsonRpcRequest
import com.efikay.wbubtw.random.service.random_org.json_rpc.JsonRpcResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.util.*

@Service
@Profile(AppConfig.PROFILE_PROD)
class RandomOrgService(
    private val restClient: RestClient,
    @Value("\${secrets.randomorg.token}") private val apiKey: String,
) : RandomService {
    private val preservedResults: MutableMap<IntRange, Stack<Int>> = mutableMapOf()

    override fun getRandomNumber(range: IntRange) = getRandomNumbers(1, range).first()

    override fun getRandomNumbers(amount: Int, range: IntRange): List<Int> = (1..amount).map {
        if (this.preservedResults[range]?.empty() == true) {
            this.grabExtraNumbersToPreserve(amount, range)
        }

        this.preservedResults[range]!!.pop()
    }

    fun getUsage() = restClient.fetchRpc<GetUsageApiParams, GetUsageApiResponse>(
        "getUsage",
        GetUsageApiParams(
            apiKey = apiKey
        )
    )

    private fun grabExtraNumbersToPreserve(amount: Int, range: IntRange) {
        assert(!range.isEmpty()) { "Range cannot be empty!" }

        val extraAmount = 30

        val response = restClient.fetchRpc<GenerateIntegersApiParams, GenerateIntegersApiResponse>(
            "generateIntegers",
            GenerateIntegersApiParams(
                apiKey,
                min = range.minOrNull()!!,
                max = range.maxOrNull()!!,
                replacement = false,
                n = amount + extraAmount
            )
        )

        val newNumbers = response?.random?.data ?: listOf()

        val rangeNumbers = preservedResults[range] ?: Stack()
        rangeNumbers.addAll(newNumbers)

        preservedResults[range] = rangeNumbers;
    }

    private inline fun <ParamsType, reified ResponseType> RestClient.fetchRpc(
        method: String,
        params: ParamsType,
    ): ResponseType? {
        val response =
            this.post().uri("https://api.random.org/json-rpc/4/invoke").body(
                JsonRpcRequest(
                    method,
                    params
                )
            ).retrieve().body<JsonRpcResponse<ResponseType>>()

        return response?.result
    }
}