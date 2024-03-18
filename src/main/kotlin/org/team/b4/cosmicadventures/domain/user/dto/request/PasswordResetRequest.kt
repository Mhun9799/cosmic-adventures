package org.team.b4.cosmicadventures.domain.user.dto.request

data class PasswordResetRequest(
    val email: String,
    val phoneNumber: String
)
