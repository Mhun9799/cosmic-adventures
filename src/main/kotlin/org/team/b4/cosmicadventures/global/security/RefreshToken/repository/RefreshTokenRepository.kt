package org.team.b4.cosmicadventures.global.security.RefreshToken.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.global.security.RefreshToken.model.RefreshToken
import org.team.b4.cosmicadventures.domain.user.model.User
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByUser(user: User): Optional<RefreshToken>
    fun deleteByToken(token: String)

}