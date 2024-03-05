package org.team.b4.cosmicadventures.domain.apod.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.apod.model.NasaImage
import org.team.b4.cosmicadventures.domain.apod.service.NasaImageService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/nasa")
class NasaApiController(
    private val nasaImageService: NasaImageService
) {
    @GetMapping("/apod")
    fun getNasaApod(): ResponseEntity<Mono<NasaImage>> {
        // 서비스를 통해 데이터베이스에 저장
        val savedNasaImage = nasaImageService.saveNasaImageFromApi()
        // 저장된 데이터를 Mono로 감싸서 반환
        return ResponseEntity.ok(Mono.just(savedNasaImage))
    }
}