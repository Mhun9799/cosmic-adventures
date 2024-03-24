package org.team.b4.cosmicadventures.domain.news.dto.request

import java.time.LocalDateTime

data class FlrDto(
    val flrID: String,
    val beginTime: LocalDateTime,
    val link: String,
)
