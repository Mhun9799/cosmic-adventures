package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.service

import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.kakao.KakaoOAuth2Client
import org.team.b4.cosmicadventures.domain.oauth2.common.JwtHelper

@Service
class KakaoOAuth2LoginService(
    private val kakaoOAuth2Client: KakaoOAuth2Client,
    private val socialMemberService: SocialMemberService,
    private val jwtHelper: JwtHelper,
) {

    fun login(authorizationCode: String): String {
        // 인가코드 -> 엑세스 토큰 발급
        // 엑세스토큰으로 사용자정보 조회
        // 사용자정보로 SocailMember 있으면 조회 없으면 회원가입
        // socialMember 우리엑세스토큰 발급후 응답
        return kakaoOAuth2Client.getAccessToken(authorizationCode)
            .let { kakaoOAuth2Client.retrieveUserInfo(it) }
            .let { socialMemberService.registerIfAbsent(it) }
            .let { jwtHelper.generateAccessToken(it.id!!) }
    }
}