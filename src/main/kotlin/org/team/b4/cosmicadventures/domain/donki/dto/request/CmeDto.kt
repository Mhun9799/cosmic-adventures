package org.team.b4.cosmicadventures.domain.donki.dto.request

import java.time.LocalDateTime

data class CmeDto(
    val cmeID: String,
    val startTime: LocalDateTime,
    val catalog: String,
    val link: String,
)