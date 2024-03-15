package org.team.b4.cosmicadventures.domain.user.dto.request

data class TemporaryPasswordRequest(
    val email: String,
    val phoneNumber: String,
    val code: String
)