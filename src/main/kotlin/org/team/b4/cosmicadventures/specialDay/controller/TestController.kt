package org.team.b4.cosmicadventures.specialDay.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.specialDay.dto.TestRequest
import org.team.b4.cosmicadventures.specialDay.service.TestService

@RestController
class TestController(private val testService: TestService) {

    @GetMapping("/api/v1/test")
    fun getUrl(@RequestBody request: TestRequest) {
        TestService(request)
    }
}
