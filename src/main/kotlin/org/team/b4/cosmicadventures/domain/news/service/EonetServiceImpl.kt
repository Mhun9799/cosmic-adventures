package org.team.b4.cosmicadventures.domain.news.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.news.dto.request.EonetDto
import org.team.b4.cosmicadventures.domain.news.model.News
import org.team.b4.cosmicadventures.domain.news.repository.NewsRepository


@Service
class EonetServiceImpl(
    private val newsRepository: NewsRepository,
    private val objectMapper: ObjectMapper,
    private val webClientBuilder: WebClient.Builder
) : EonetService {
    companion object {
        const val API_KEY = "CF7Jy0Yjlf3vGkZLVpS2ZF3nYWmatYUVfcZMxhuR"
    }

    override fun saveAllFromApi() {
        saveFromApi("https://eonet.gsfc.nasa.gov/api/v2.1/events?days=3&api_key=$API_KEY", eonetResponseExtractor)
    }

    override fun saveFromApi(apiUrl: String, extractor: (JsonNode) -> News) {
        val client = webClientBuilder.build()
        val responseSpec = client.get()
            .uri(apiUrl)
            .retrieve()
        val response = responseSpec.bodyToMono(String::class.java).block()

        if (!response.isNullOrEmpty()) {
            val eoNetNode = objectMapper.readTree(response)

            eoNetNode.fields().asSequence().forEach { (date, eoNetList) ->
                val newsList = eoNetList.mapNotNull { eoNetNode ->
                    extractor(eoNetNode)
                }
                newsRepository.saveAll(newsList)
            }
        }
    }

    val eonetResponseExtractor: (JsonNode) -> News = { eoNetNode -> // Update the extractor
        val eonetDto = EonetDto(
            eonetID = eoNetNode["id"]?.asText() ?: "NULL",
            title = eoNetNode["title"]?.asText() ?: "NULL",
            link = eoNetNode["link"]?.asText() ?: "NULL",
            description = eoNetNode["description"]?.asText() ?: "NULL",
        )

        News(
            title = eonetDto.title,
            body = "이름은 ${eonetDto.title}, 관련정보 링크는 ${eonetDto.link}입니다.",
            detail = """
                ID = ${eonetDto.eonetID}
                title = ${eonetDto.title}
                link = ${eonetDto.link}
                description = ${eonetDto.description}
            """.trimIndent()
        )
    }
}