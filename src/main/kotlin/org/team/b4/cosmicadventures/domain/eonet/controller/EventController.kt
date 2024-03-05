package org.team.b4.cosmicadventures.domain.eonet.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.eonet.service.NasaEventService


@RestController
@RequestMapping("/api/v1")
class EventController(
    private val nasaEventService: NasaEventService
) {
    //Nasa Api 호출
    @GetMapping("/nasa/events")
    fun getNasaEvent(): String =
       nasaEventService.getNasaEventApi()
}