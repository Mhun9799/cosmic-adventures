package org.team.b4.cosmicadventures.global.security.RefreshToken.service

import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.global.security.RefreshToken.model.RefreshToken
import org.team.b4.cosmicadventures.global.security.RefreshToken.repository.RefreshTokenRepository
import org.team.b4.cosmicadventures.domain.user.model.User


@Service
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,

    ) : RefreshTokenService {

    override fun saveRefreshToken(user: User, refreshToken: String) {
        refreshTokenRepository.save(RefreshToken(user = user, token = refreshToken))
    }

    override fun findRefreshToken(user: User): String? {
        val refreshTokenOptional = refreshTokenRepository.findByUser(user)
        return refreshTokenOptional.orElse(null)?.token
    }

}


