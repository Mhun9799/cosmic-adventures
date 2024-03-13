package org.team.b4.cosmicadventures.domain.oauth2.api.oauth2login.service

import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.OAuth2LoginUserInfo
import org.team.b4.cosmicadventures.domain.user.model.Role
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository


@Service
class SocialMemberService(
    private val userRepository: UserRepository
) {


    fun registerIfAbsent(userInfo: OAuth2LoginUserInfo): User {
        val existingUser = userRepository.findByProviderAndProviderId(userInfo.provider, userInfo.id)
        return existingUser ?: run {
            val newUser = User(
                provider = userInfo.provider,
                providerId = userInfo.id,
                name = userInfo.nickname,
                email = userInfo.email,
                password = "",
                introduction = "",
                tlno = "",
                role = Role.USER
            )
            userRepository.save(newUser)
        }
    }
}