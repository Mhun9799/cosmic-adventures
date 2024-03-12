package org.team.b4.cosmicadventures.domain.donki.dto.request

import java.time.LocalDateTime

data class GstDto(
    val gstID: String,
    val startTime: LocalDateTime,
    val link: String,
)