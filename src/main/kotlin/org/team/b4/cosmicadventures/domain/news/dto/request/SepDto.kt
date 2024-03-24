package org.team.b4.cosmicadventures.domain.news.dto.request

import java.time.LocalDateTime

data class SepDto(
    val sepID: String,
    val eventTime: LocalDateTime,
    val link: String,
)