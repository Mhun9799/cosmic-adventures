package org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.naver.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.team.b4.cosmicadventures.domain.oauth2.client.oauth2.OAuth2LoginUserInfo
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class NaverLoginUserInfoResponse(
    id: String,
    nickname: String,
    email:String?
) : OAuth2LoginUserInfo(
    provider = OAuth2Provider.NAVER,
    id = id,
    nickname = nickname,
    email = "",
)