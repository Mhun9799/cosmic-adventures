package org.team.b4.cosmicadventures.domain.apod.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.apod.model.NasaImage
import org.team.b4.cosmicadventures.domain.apod.repository.NasaImageRepository
import java.net.URL
import java.time.LocalDate

@Service
class NasaImageService(
    private val nasaImageRepository: NasaImageRepository
) {
    // NASA API에서 이미지 정보를 가져와서 데이터베이스에 저장하는 메서드
    @Cacheable("nasaImageCache")
    fun saveNasaImageFromApi(): NasaImage {
        // WebClient를 사용하여 외부 API에 HTTP 요청을 보냄
        val client = WebClient.create()
        // API 응답을 받아옴
        val responseSpec = client.get()
            .uri("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY")
            .retrieve()
        // API 응답을 Mono<String>으로 받아온 뒤 blocking으로 값을 추출 (실제 코드에서는 이 부분을 비동기로 처리하는 것이 더 좋음)
        val apodResponse = responseSpec.bodyToMono(String::class.java).block()
        // API 응답에서 NasaImage 객체를 추출
        val nasaImage = extractNasaImageFromApiResponse(apodResponse)
        // 추출한 NasaImage 객체를 데이터베이스에 저장하고 저장된 객체를 반환
        return nasaImageRepository.save(nasaImage)
    }
    // NASA API 응답에서 NasaImage 객체를 추출하는 메서드
    private fun extractNasaImageFromApiResponse(apodResponse: String?): NasaImage {
        // Jackson ObjectMapper를 사용하여 JSON 형식의 API 응답을 파싱
        val objectMapper = ObjectMapper()
        val node = objectMapper.readTree(apodResponse)
        // JSON 노드에서 필요한 정보를 추출
        val title = node["title"].asText()
        val explanation = node["explanation"]?.asText()
        val date = LocalDate.parse(node["date"].asText())
        val hdurl = URL(node["hdurl"].asText())
        val mediaType = node["media_type"].asText()
        val serviceVersion = node["service_version"].asText()
        val url = URL(node["url"].asText())
        // 추출한 정보로 NasaImage 객체 생성 및 반환
        return NasaImage(title, explanation, date, hdurl, mediaType, serviceVersion, url)
    }
}