package com.efikay.wbubtw.shared.api_clients.meme_api

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class MemeApiService(
    private val restClient: RestClient
) {
    fun getOne(): GetMemeApiResponse? {
        return restClient
            .get()
            .uri(BASE_URL)
            .retrieve()
            .body<GetMemeApiResponse>()
    }

    fun getMany(amount: Int): GetMultipleMemesApiResponse? {
        return restClient
            .get()
            .uri("$BASE_URL/$amount")
            .retrieve()
            .body<GetMultipleMemesApiResponse>()
    }

    companion object {
        private const val BASE_URL = "https://meme-api.com/gimme"
    }
}