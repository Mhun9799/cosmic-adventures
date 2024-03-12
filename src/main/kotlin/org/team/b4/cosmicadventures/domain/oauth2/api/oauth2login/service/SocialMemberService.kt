package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.service

import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.kakao.dto.KakaoUserInfoResponse
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.SocialMember
import org.team.b4.cosmicadventures.domain.oauth2.domain.repository.SocialMemberRepository

@Service
class SocialMemberService(
    private val socialMemberRepository: SocialMemberRepository
) {
    fun registerIfAbsent(userInfo: KakaoUserInfoResponse): SocialMember {
       return if (!socialMemberRepository.existsByProviderAndProviderId(OAuth2Provider.KAKAO, userInfo.id.toString())){
            val socialMember = SocialMember.ofKakao(userInfo.id, userInfo.nickname)
            socialMemberRepository.save(socialMember)
        }else{
            socialMemberRepository.findByProviderAndProviderId(OAuth2Provider.KAKAO, userInfo.id.toString())
        }
    }
}