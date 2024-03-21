package org.team.b4.cosmicadventures.domain.iss.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.iss.dto.AstronautResponse
import org.team.b4.cosmicadventures.domain.iss.dto.IssLocationAndAstronautsResponse
import org.team.b4.cosmicadventures.domain.iss.dto.IssLocationResponse

@Service
class IssLocationService(
) {

    private val locationApiUrl = "http://api.open-notify.org/iss-now.json"
    private val astronautsApiUrl = "http://api.open-notify.org/astros.json"

    fun getIssLocation(): IssLocationResponse {
        val locationApiClient= WebClient.create()
        val locationResponse = locationApiClient.get()
            .uri(locationApiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw IllegalStateException("ISS 위치를 가져오는 데 실패했습니다.")
        val objectMapper = ObjectMapper()
        val locationJson: JsonNode = objectMapper.readTree(locationResponse)
        val issLocation = IssLocationResponse(
            latitude = locationJson["iss_position"]["latitude"].asDouble(),
            longitude = locationJson["iss_position"]["longitude"].asDouble()
        )
        return issLocation
    }

    fun getAstronauts(): List<AstronautResponse> {
        val astronautsApiClient= WebClient.create()
        val astronautsResponse = astronautsApiClient.get()
            .uri(astronautsApiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw IllegalStateException("우주 비행사 정보를 가져오는 데 실패했습니다.")
        val objectMapper = ObjectMapper()
        val astronautsJson: JsonNode = objectMapper.readTree(astronautsResponse)
        val astronauts = astronautsJson["people"].map {
            AstronautResponse(
                name = it["name"].asText(),
                craft = it["craft"].asText()
            )
        }
        return astronauts.toList()
    }

    fun getIssLocationAndAstronauts(): IssLocationAndAstronautsResponse {
        val issLocation = getIssLocation()
        val astronauts = getAstronauts()

        return IssLocationAndAstronautsResponse(issLocation, astronauts)
    }

    fun getClientIp(request: HttpServletRequest): String {
        var ip: String? = request.getHeader("X-Forwarded-For")
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_FORWARDED")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_FORWARDED")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_VIA")
        }
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        return ip.orEmpty()
    }


}

//

