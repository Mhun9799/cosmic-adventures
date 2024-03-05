package org.team.b4.cosmicadventures.domain.user.dto

data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val role: String,
)
