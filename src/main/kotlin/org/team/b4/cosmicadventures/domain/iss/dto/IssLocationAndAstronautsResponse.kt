package org.team.b4.cosmicadventures.domain.iss.dto

data class IssLocationAndAstronautsResponse(
    val issLocation: IssLocationResponse,
    val astronauts: List<AstronautResponse>
)