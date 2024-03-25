package org.team.b4.cosmicadventures.domain.apod.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.apod.model.NasaImage
import org.team.b4.cosmicadventures.domain.apod.service.NasaImageService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/nasa")
class NasaApiController(
    private val nasaImageService: NasaImageService
) {
    @GetMapping("/apod")
    fun getNasaApod(): ResponseEntity<Mono<NasaImage>> {

        val savedNasaImage = nasaImageService.saveNasaImageFromApi()
        return ResponseEntity.ok(Mono.just(savedNasaImage))
    }
}