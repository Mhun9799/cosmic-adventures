package org.team.b4.cosmicadventures.domain.donki.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.donki.service.DonkiService

@RestController
class DonkiController(
    private val donkiService: DonkiService
) {
    @GetMapping("/mpc")
    fun saveMpcFromApi(): ResponseEntity<Unit> {
        donkiService.saveMpcFromApi()
        return ResponseEntity.ok(Unit)
    }
    @GetMapping("/sep")
    fun saveSepFromApi(): ResponseEntity<Unit> {
        donkiService.saveSepFromApi()
        return ResponseEntity.ok(Unit)
    }
    @GetMapping("/cme")
    fun saveCmeFromApi(): ResponseEntity<Unit> {
        donkiService.saveCmeFromApi()
        return ResponseEntity.ok(Unit)
    }
}