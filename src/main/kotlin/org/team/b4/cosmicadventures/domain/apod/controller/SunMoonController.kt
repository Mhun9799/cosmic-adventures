package org.team.b4.cosmicadventures.domain.apod.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.apod.service.SunMoonInfoService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/sunmoon")
class SunMoonInfoController(private val sunMoonInfoService: SunMoonInfoService) {

    @GetMapping("/rise-set", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRiseSetInfo(
        @RequestParam location: String,
        @RequestParam locdate: String
    ): Mono<Map<String, Any>> {
        return sunMoonInfoService.getLocalRiseSetInfo(location, locdate)
            .flatMap { xmlString -> sunMoonInfoService.parseXmlToMap(xmlString) }
    }
}