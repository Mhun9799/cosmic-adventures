package org.team.b4.cosmicadventures.domain.marsrover.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.marsrover.dto.MarsRoverResponse
import org.team.b4.cosmicadventures.domain.marsrover.model.MarsRover
import org.team.b4.cosmicadventures.domain.marsrover.repository.MarsRoverRepository
import kotlin.random.Random

@Service
class MarsRoverService(
    private val marsRoverRepository: MarsRoverRepository
) {

    fun getRecentMarsRoverData(): MarsRoverResponse? {
        val apiKey = "HmUXvuh5RRIGhptnbNPmrHbdV2MVGy8nwAIpApCd"
        // NASA API를 통해 최신 사진을 가져오기 위한 엔드포인트
        val apiUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/latest_photos?api_key=$apiKey"
        val client = WebClient.create()
        // WebClient를 사용하여 외부 API에 HTTP 요청을 보냄
        val response = client.get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        // API 응답을 객체로 매핑
        val objectMapper = ObjectMapper()
        val photosArray = objectMapper.readTree(response).get("latest_photos")
        // 최근 정보 중 랜덤하게 선택
        val randomPhoto = if (photosArray.isArray && photosArray.size() > 0) {
            val randomIndex = Random.nextInt(photosArray.size())
            val selectedPhoto = photosArray.get(randomIndex)
            // 필요한 정보만 추출하여 MarsRoverResponse 객체 생성
            val imgSrc = selectedPhoto.get("img_src").asText()
            val earthDate = selectedPhoto.get("earth_date").asText()
            val roverName = selectedPhoto.get("rover").get("name").asText()
            val cameraName = selectedPhoto.get("camera").get("name").asText()

            val marsRover = MarsRover(
                imgSrc = imgSrc,
                earthDate = earthDate,
                roverName = roverName,
                cameraName = cameraName)
            marsRoverRepository.save(marsRover)

            MarsRoverResponse(imgSrc, earthDate, roverName, cameraName)
        } else {
            null
        }

        return randomPhoto
    }
}