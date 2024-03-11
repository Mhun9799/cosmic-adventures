package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.service.KakaoOAuth2LoginService
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.kakao.KakaoOAuth2Client


@RestController
class KakaoOAuth2LoginController(
    private val kakaoOAuth2LoginService: KakaoOAuth2LoginService,
    private val kakaoOAuth2Client: KakaoOAuth2Client
) {


    // 로그인 페이지로 redirect 해주는 API
    @GetMapping("/oauth2/login/kakao")
    fun redirectLoginPage(response: HttpServletResponse) {
        val loginPageUrl = kakaoOAuth2Client.generateLoginPageUrl()
        response.sendRedirect(loginPageUrl)
    }


    // code를 이용해 사용자 인증처리 API
    @GetMapping("/oauth2/callback/kakao")
    fun callback(@RequestParam(name = "code") authorizationCode:String):String{
        return kakaoOAuth2LoginService.login(authorizationCode)
    }

}