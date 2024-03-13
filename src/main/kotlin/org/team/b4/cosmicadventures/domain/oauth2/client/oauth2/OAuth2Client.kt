package org.team.b4.cosmicadventures.domain.oauth2.client.oauth2

import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider

interface OAuth2Client {

    fun generateLoginPageUrl():String
    fun getAccessToken(authorizationCode:String):String
    fun retrieveUserInfo(accessToken:String) : OAuth2LoginUserInfo
    fun supports(provider: OAuth2Provider): Boolean
}