package org.team.b4.cosmicadventures.domain.user.dto

data class LoginRequest(
    val email:String,
    val password: String,
    val role:String,

)
