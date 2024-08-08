package com.efikay.wbubtw.random.service.random_org.json_rpc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
private class JsonRpcClientConfig {
    @Bean
    fun restClient(): RestClient {
        return RestClient.create()
    }
}