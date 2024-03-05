package org.team.b4.cosmicadventures.domain.marsrover.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.marsrover.dto.MarsRoverResponse
import org.team.b4.cosmicadventures.domain.marsrover.service.MarsRoverService


@RestController
@RequestMapping("/api/v1/mars-rovers")
class MarsRoverController(
    private val marsRoverService: MarsRoverService
) {


    @GetMapping("/recent")
    fun getRecentMarsRoverData(): MarsRoverResponse? {
        return marsRoverService.getRecentMarsRoverData()
    }
}
