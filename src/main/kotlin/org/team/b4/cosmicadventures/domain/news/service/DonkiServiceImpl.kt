package org.team.b4.cosmicadventures.domain.news.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.news.dto.request.*
import org.team.b4.cosmicadventures.domain.news.model.News
import org.team.b4.cosmicadventures.domain.news.repository.NewsRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class DonkiServiceImpl(
    private val newsRepository: NewsRepository,
    private val objectMapper: ObjectMapper,
    private val webClientBuilder: WebClient.Builder
) : DonkiService {
    companion object {
        const val API_KEY = "CF7Jy0Yjlf3vGkZLVpS2ZF3nYWmatYUVfcZMxhuR"
        const val BASE_URL = "https://api.nasa.gov/DONKI"
    }

//    @Scheduled(cron = "0 0 0 * * ?")
//    fun saveAllFromApi() {
//        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
//        saveFromApi("$BASE_URL/MPC?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", mpcResponseExtractor)
//        saveFromApi("$BASE_URL/SEP?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", sepResponseExtractor)
//        saveFromApi("$BASE_URL/CME?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", cmeResponseExtractor)
//    }
// 스케쥴러로 정해진 시각에 불러오는 로직입니다. 테스트해야 하기 때문에 일단 주석처리


    override fun saveAllFromApi() {
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        saveFromApi("$BASE_URL/MPC?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", mpcResponseExtractor)
        saveFromApi("$BASE_URL/SEP?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", sepResponseExtractor)
        saveFromApi("$BASE_URL/CME?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", cmeResponseExtractor)
        saveFromApi("$BASE_URL/GST?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", gstResponseExtractor)
        saveFromApi("$BASE_URL/RBE?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", rbeResponseExtractor)
        saveFromApi("$BASE_URL/FLR?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", flrResponseExtractor)
        saveFromApi("$BASE_URL/HSS?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", hssResponseExtractor)
    }

    override fun saveFromApi(apiUrl: String, extractor: (JsonNode) -> News) {
        val client = webClientBuilder.build()
        val responseSpec = client.get()
            .uri(apiUrl)
            .retrieve()
        val response = responseSpec.bodyToMono(String::class.java).block()

        if (!response.isNullOrEmpty()) {
            val nodeList = objectMapper.readTree(response)
            val newsList = nodeList.mapNotNull { node -> extractor(node) }
            newsRepository.saveAll(newsList) // EntityManager 사용
        }
    }

    val mpcResponseExtractor: (JsonNode) -> News = { node ->
        val eventTimeString = node["eventTime"].asText().replace("Z", "")
        val eventTime = LocalDateTime.parse(eventTimeString)

        val mpcDto = MpcDto(
            mpcID = node["mpcID"].asText(),
            eventTime = eventTime,
            link = node["link"].asText(),
        )
        News(
            title = "Magnetopause Crossing",
            body = "관측시간은 ${mpcDto.eventTime}, 관련정보 링크는 ${mpcDto.link}입니다.",
            detail = """
                ID = ${mpcDto.mpcID}
                eventTime = ${mpcDto.eventTime}
                link = ${mpcDto.link}
            """.trimIndent()
        )
    }

    val sepResponseExtractor: (JsonNode) -> News = { node ->
        val eventTimeString = node["eventTime"].asText().replace("Z", "")
        val eventTime = LocalDateTime.parse(eventTimeString)

        val sepDto = SepDto(
            sepID = node["sepID"].asText(),
            eventTime = eventTime,
            link = node["link"].asText(),
        )
        News(
            title = "Solar Energetic Particle",
            body = "관측시간은 ${sepDto.eventTime}, 관련정보 링크는 ${sepDto.link}입니다.",
            detail = """
                ID = ${sepDto.sepID}
                eventTime = ${sepDto.eventTime}
                link = ${sepDto.link}
            """.trimIndent()
        )
    }

    val cmeResponseExtractor: (JsonNode) -> News = { node ->
        val startTimeString = node["startTime"].asText().replace("Z", "")
        val startTime = LocalDateTime.parse(startTimeString)

        val cmeDto = CmeDto(
            cmeID = node["activityID"].asText(),
            startTime = startTime,
            catalog = node["catalog"].asText(),
            link = node["link"].asText(),
        )

        News(
            title = "Coronal Mass Ejection",
            body = "관측시간은 ${cmeDto.startTime}, 카탈로그는 ${cmeDto.catalog} 관련정보 링크는 ${cmeDto.link}입니다.",
            detail = """
                ID = ${cmeDto.cmeID}
                eventTime = ${cmeDto.startTime}
                catalog = ${cmeDto.catalog}
                link = ${cmeDto.link}
            """.trimIndent()
        )
    }

    val gstResponseExtractor: (JsonNode) -> News = { node ->
        val startTimeString = node["startTime"].asText().replace("Z", "")
        val startTime = LocalDateTime.parse(startTimeString)

        val gstDto = GstDto(
            gstID = node["gstID"].asText(),
            startTime = startTime,
            link = node["link"].asText(),
        )

        News(
            title = "Geomagnetic Storm",
            body = "관측시간은 ${gstDto.startTime}, 관련정보 링크는 ${gstDto.link}입니다.",
            detail = """
                ID = ${gstDto.gstID}
                eventTime = ${gstDto.startTime}
                link = ${gstDto.link}
            """.trimIndent()
        )
    }

    val rbeResponseExtractor: (JsonNode) -> News = { node ->
        val eventTimeString = node["eventTime"].asText().replace("Z", "")
        val eventTime = LocalDateTime.parse(eventTimeString)

        val rbeDto = RbeDto(
            rbeID = node["rbeID"].asText(),
            eventTime = eventTime,
            link = node["link"].asText(),
        )
        News(
            title = "Radiation Belt Enhancement",
            body = "관측시간은 ${rbeDto.eventTime}, 관련정보 링크는 ${rbeDto.link}입니다.",
            detail = """
                ID = ${rbeDto.rbeID}
                eventTime = ${rbeDto.eventTime}
                link = ${rbeDto.link}
            """.trimIndent()
        )
    }
    val flrResponseExtractor: (JsonNode) -> News = { node ->
        val beginTimeString = node["beginTime"].asText().replace("Z", "")
        val beginTime = LocalDateTime.parse(beginTimeString)

        val flrDto = FlrDto(
            flrID = node["flrID"].asText(),
            beginTime = beginTime,
            link = node["link"].asText(),
        )
        News(
            title = "Solar Flare",
            body = "관측시간은 ${flrDto.beginTime}, 관련정보 링크는 ${flrDto.link}입니다.",
            detail = """
                ID = ${flrDto.flrID}
                eventTime = ${flrDto.beginTime}
                link = ${flrDto.link}
            """.trimIndent()
        )
    }

    val hssResponseExtractor: (JsonNode) -> News = { node ->
        val eventTimeString = node["eventTime"].asText().replace("Z", "")
        val eventTime = LocalDateTime.parse(eventTimeString)

        val hssDto = HssDto(
            hssID = node["hssID"].asText(),
            eventTime = eventTime,
            link = node["link"].asText(),
        )
        News(
            title = "Hight Speed Stream",
            body = "관측시간은 ${hssDto.eventTime}, 관련정보 링크는 ${hssDto.link}입니다.",
            detail = """
                ID = ${hssDto.hssID}
                eventTime = ${hssDto.eventTime}
                link = ${hssDto.link}
            """.trimIndent()
        )
    }


}

