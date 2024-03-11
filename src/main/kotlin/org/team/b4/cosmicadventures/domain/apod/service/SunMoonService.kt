package org.team.b4.cosmicadventures.domain.apod.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SunMoonInfoService {
    fun parseXmlToMap(xmlString: String): Mono<Map<String, Any>> {
        val xmlMapper = XmlMapper()
        return Mono.fromCallable { xmlMapper.readValue(xmlString, object : TypeReference<Map<String, Any>>() {}) }
    }
    fun getLocalRiseSetInfo(location: String, locdate: String): Mono<String> {
        val apiUrl = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo?" +
                "location=$location&locdate=$locdate&ServiceKey=iBAqI3J5FeElaoJyMFWwmxBgRGhv7IWXaipjTzRQF686ozG2g29KxDC2o1VfUP0qQaM0QRIreUy5xbXTpaZYmw=="
        val client = WebClient.builder()
            .codecs { it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder()) }
            .build()
        return client.get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .doOnError { error -> throw RuntimeException("Failed to retrieve SunMoonInfo data: ${error.message}") }
    }
}