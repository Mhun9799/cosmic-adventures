package org.team.b4.cosmicadventures.domain.news.dto.request

import java.time.LocalDateTime

data class GstDto(
    val gstID: String,
    val startTime: LocalDateTime,
    val link: String,
)