package org.team.b4.cosmicadventures.domain.donki.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.donki.service.DonkiService
import org.team.b4.cosmicadventures.domain.donki.service.DonkiServiceImpl

@RestController
class DonkiController(
    private val donkiService: DonkiService
) {
    @GetMapping("/saveNews")
    fun saveNewsFromApi(): ResponseEntity<Unit> {
        donkiService.saveAllFromApi()
        return ResponseEntity.ok(Unit)
    }
}