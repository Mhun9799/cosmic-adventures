package org.team.b4.cosmicadventures.domain.news.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.news.service.NeowsService
import org.team.b4.cosmicadventures.domain.news.service.DonkiService
import org.team.b4.cosmicadventures.domain.news.service.EonetService

@RestController
class NewsController(
    private val donkiService: DonkiService,
    private val neowsService: NeowsService,
    private val eonetService: EonetService,
) {
    @GetMapping("/saveDonkiNews")
    fun saveDonkiNewsFromApi(): ResponseEntity<Unit> {
        donkiService.saveAllFromApi()
        return ResponseEntity.ok(Unit)
    }
    @GetMapping("/saveNeowsNews")
    fun saveNeowsNewsFromApi(): ResponseEntity<Unit> {
        neowsService.saveAllFromApi()
        return ResponseEntity.ok(Unit)
    }
    @GetMapping("/saveEonetNews")
    fun saveEonetNewsFromApi(): ResponseEntity<Unit> {
        eonetService.saveAllFromApi()
        return ResponseEntity.ok(Unit)
    }
}