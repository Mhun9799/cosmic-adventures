package org.team.b4.cosmicadventures.domain.iss.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.iss.dto.AstronautResponse
import org.team.b4.cosmicadventures.domain.iss.dto.IssLocationAndAstronautsResponse
import org.team.b4.cosmicadventures.domain.iss.dto.IssLocationResponse
import org.team.b4.cosmicadventures.domain.iss.service.IssLocationService

@RestController
@RequestMapping("/api/v1/iss")
class IssLocationController(
    private val issLocationService: IssLocationService,
) {

    //ISS위성의 위치
    @GetMapping("/location")
    fun getIssLocation(): IssLocationResponse {
        return issLocationService.getIssLocation()
    }
    //ISS위성의 직원목록
    @GetMapping("/astronauts")
    fun getAstronauts(): List<AstronautResponse> {
        return issLocationService.getAstronauts()
    }
    //같이 반환
    @GetMapping("/location-and-astronauts")
    fun getIssLocationAndAstronauts(): IssLocationAndAstronautsResponse {
        return issLocationService.getIssLocationAndAstronauts()
    }

    @GetMapping("/ip")
    fun getClientIp(request: HttpServletRequest): String {
        return issLocationService.getClientIp(request)
    }
}