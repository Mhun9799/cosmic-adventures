package org.team.b4.cosmicadventures.domain.oauth2.common

import org.springframework.stereotype.Component


@Component
class JwtHelper {
    fun generateAccessToken(id: Long?): String {
        return "SampleAccessToken $id"
    }
}