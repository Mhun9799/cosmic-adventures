package org.team.b4.cosmicadventures.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class SlangFilterServiceConfig {
    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }
}