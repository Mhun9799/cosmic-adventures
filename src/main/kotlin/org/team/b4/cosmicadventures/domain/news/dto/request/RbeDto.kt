package org.team.b4.cosmicadventures.domain.news.dto.request

import java.time.LocalDateTime

data class RbeDto(
    val rbeID: String,
    val eventTime: LocalDateTime,
    val link: String,
)
