package org.team.b4.cosmicadventures.domain.user.dto.request

import org.team.b4.cosmicadventures.global.validation.ValidPassword


data class UpdateUserPasswordRequest(

    @field: ValidPassword
    val userPassword:String,

    @field: ValidPassword
    val userNewPassword: String
)
