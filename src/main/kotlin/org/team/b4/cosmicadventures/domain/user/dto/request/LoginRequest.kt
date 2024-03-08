package org.team.b4.cosmicadventures.domain.user.dto.request


import jakarta.validation.constraints.Email
import org.springframework.validation.annotation.Validated
import org.team.b4.cosmicadventures.global.validation.ValidPassword

@Validated
data class LoginRequest(

    @field: Email
    val email:String,

    @field: ValidPassword
    val password: String,

    val role : String,

    val verificationCode: String
)
