package org.team.b4.cosmicadventures.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.converter.OAuth2ProviderConverter
import org.team.b4.cosmicadventures.global.security.TokenRefreshInterceptor


@Configuration
class WebConfig(
    private val tokenRefreshInterceptor: TokenRefreshInterceptor
): WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(OAuth2ProviderConverter())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(tokenRefreshInterceptor)
    }

}
