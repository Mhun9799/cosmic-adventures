package org.team.b4.cosmicadventures.domain.news.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.news.dto.request.NeowsDto
import org.team.b4.cosmicadventures.domain.news.model.News
import org.team.b4.cosmicadventures.domain.news.repository.NewsRepository

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class NeowsServiceImpl(
    private val newsRepository: NewsRepository,
    private val objectMapper: ObjectMapper,
    private val webClientBuilder: WebClient.Builder
) : NeowsService {
    companion object {
        const val API_KEY = "CF7Jy0Yjlf3vGkZLVpS2ZF3nYWmatYUVfcZMxhuR"
        const val BASE_URL = "https://api.nasa.gov/neo/rest/v1"
    }

    override fun saveAllFromApi() {
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        saveFromApi("$BASE_URL/feed?start_date=$yesterday&end_date=$yesterday&api_key=$API_KEY", neowsResponseExtractor)

    }

    override fun saveFromApi(apiUrl: String, extractor: (JsonNode) -> News) {
        val client = webClientBuilder.build()
        val responseSpec = client.get()
            .uri(apiUrl)
            .retrieve()
        val response = responseSpec.bodyToMono(String::class.java).block()

        if (!response.isNullOrEmpty()) {
            val nearEarthObjectsNode = objectMapper.readTree(response).path("near_earth_objects")

            nearEarthObjectsNode.fields().asSequence().forEach { (date, asteroidList) ->
                val newsList = asteroidList.mapNotNull { asteroidNode ->
                    extractor(asteroidNode)
                }
                newsRepository.saveAll(newsList)
            }
        }
    }

    val neowsResponseExtractor: (JsonNode) -> News = { asteroidNode -> // Update the extractor
        val neoID = asteroidNode["neo_reference_id"]?.asText() ?: "NULL"
        val name = asteroidNode["name"]?.asText() ?: "NULL"
        val jplUrl = asteroidNode["nasa_jpl_url"]?.asText() ?: "NULL"

        News(
            title = neoID,
            body = "이름은 ${name}, 자세한 정보는 ${jplUrl}입니다.",
            detail = """
            ID = ${neoID}
            name = ${name}
            link = ${jplUrl}
        """.trimIndent()
        )
    }
}