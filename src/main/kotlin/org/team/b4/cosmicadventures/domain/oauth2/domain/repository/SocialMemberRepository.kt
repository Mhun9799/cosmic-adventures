package org.team.b4.cosmicadventures.domain.oauth2.domain.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.SocialMember


@Repository
interface SocialMemberRepository : CrudRepository<SocialMember, Long> {
    fun existsByProviderAndProviderId(kakao: OAuth2Provider, toString: String): Boolean
    fun findByProviderAndProviderId(kakao: OAuth2Provider, toString: String): SocialMember
}