package org.team.b4.cosmicadventures.domain.eonet.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient

@Service
@Transactional
class NasaEventService {
    fun getNasaEventApi(): String {
        val apiKey = "DEMO_KEY"
        val apiUrl = "https://eonet.gsfc.nasa.gov/api/v3/sources?api_key=$apiKey"

        val client = WebClient.create()
        val response = client.get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        val objectMapper = ObjectMapper()
        val prettyResponse = objectMapper.readValue(response, Any::class.java)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(prettyResponse)
    }
}