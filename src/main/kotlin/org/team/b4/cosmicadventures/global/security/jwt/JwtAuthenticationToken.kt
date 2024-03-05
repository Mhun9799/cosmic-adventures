package org.team.b4.cosmicadventures.global.security.jwt


import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.team.b4.cosmicadventures.global.security.UserPrincipal

class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    details: WebAuthenticationDetails
) : AbstractAuthenticationToken(principal.authorities) {

    init {
        super.setAuthenticated(true)
        super.setDetails(details)
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated(): Boolean {
        return true
    }
}
