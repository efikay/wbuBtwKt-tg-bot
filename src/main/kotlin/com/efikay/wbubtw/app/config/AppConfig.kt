package com.efikay.wbubtw.app.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class AppConfig {
    companion object {
        const val PROFILE_DEV = "dev"
        const val PROFILE_PROD = "prod"
    }
}
