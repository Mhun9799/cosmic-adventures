package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.service.OAuth2LoginService
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.OAuth2ClientService
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider


@RestController
class OAuth2LoginController(
    private val oAuth2LoginService: OAuth2LoginService,
    private val oAuth2ClientService: OAuth2ClientService
) {

    @GetMapping("/oauth2/login/{provider}")
    fun redirectLoginPage(@PathVariable provider: OAuth2Provider, response: HttpServletResponse) {
        oAuth2ClientService.generateLoginPageUrl(provider)
            .let { response.sendRedirect(it) }
    }

    @GetMapping("/oauth2/callback/{provider}")
    fun callback(
        @PathVariable provider: OAuth2Provider,
        @RequestParam(name = "code") authorizationCode: String,
        response: HttpServletResponse
    ) {
        oAuth2LoginService.login(provider, authorizationCode, response)
        // 리다이렉트할 URL로 설정
        val redirectUrl = "http://localhost:9000"
        response.sendRedirect(redirectUrl)
    }
}