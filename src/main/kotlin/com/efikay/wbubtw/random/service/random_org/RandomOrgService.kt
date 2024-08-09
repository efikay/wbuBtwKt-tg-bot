package com.efikay.wbubtw.random.service.random_org

import com.efikay.wbubtw.app.config.AppConfig
import com.efikay.wbubtw.random.service.RandomService
import com.efikay.wbubtw.random.service.random_org.api_types.GenerateIntegersApiResponse
import com.efikay.wbubtw.random.service.random_org.api_types.GenerateIntegersParams
import com.efikay.wbubtw.random.service.random_org.api_types.GetUsageParams
import com.efikay.wbubtw.random.service.random_org.api_types.GetUsageResponse
import com.efikay.wbubtw.random.service.random_org.json_rpc.JsonRpcRequest
import com.efikay.wbubtw.random.service.random_org.json_rpc.JsonRpcResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
@Profile(AppConfig.PROFILE_PROD)
class RandomOrgService(
    private val restClient: RestClient,
    @Value("\${secrets.randomorg.token}") val apiKey: String
) : RandomService {
    override fun getRandomNumbers(amount: UInt): List<Int> {
        val response = restClient.fetchRpc<GenerateIntegersParams, GenerateIntegersApiResponse>(
            "generateIntegers",
            GenerateIntegersParams(
                apiKey,
                min = 0,
                max = 100,
                replacement = false,
                n = amount
            )
        )

        return response?.random?.data ?: listOf()
    }

    fun getUsage() = restClient.fetchRpc<GetUsageParams, GetUsageResponse>(
        "getUsage",
        GetUsageParams(
            apiKey = apiKey
        )
    )

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