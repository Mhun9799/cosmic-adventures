package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.service

import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.OAuth2ClientService
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider
import org.team.b4.cosmicadventures.global.security.jwt.JwtPlugin

@Service
class OAuth2LoginService(
    private val oAuth2ClientService: OAuth2ClientService,
    private val socialMemberService: SocialMemberService,
    private val jwtPlugin: JwtPlugin,
) {
    // 인가코드 -> 엑세스 토큰 발급
    // 엑세스토큰으로 사용자정보 조회
    // 사용자정보로 SocailMember 있으면 조회 없으면 회원가입
    // socialMember 우리엑세스토큰 발급후 응답
    fun login(provider: OAuth2Provider, authorizationCode: String): String {
        val socialMember = oAuth2ClientService.login(provider, authorizationCode)
            .let { socialMemberService.registerIfAbsent(it) }
        val subject = socialMember.id.toString()
        val email = socialMember.email
        val role = "USER"
        return jwtPlugin.generateAccessToken(subject, email, role)
    }
}