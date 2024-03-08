package org.team.b4.cosmicadventures.global.security.RefreshToken.service

import org.team.b4.cosmicadventures.domain.user.model.User


interface RefreshTokenService {

    fun saveRefreshToken(user: User, refreshToken: String)
    fun findRefreshToken(user: User): String?
}