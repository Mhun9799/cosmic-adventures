package org.team.b4.cosmicadventures.domain.oauth2.client.oauth2

import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider

open class OAuth2LoginUserInfo(
    val provider: OAuth2Provider,
    val id: String,
    val nickname: String,
    val email:String,
)