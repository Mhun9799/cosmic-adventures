package org.team.b4.cosmicadventures.domain.donki.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.donki.dto.request.*
//import org.team.b4.cosmicadventures.domain.donki.model.Instrument
//import org.team.b4.cosmicadventures.domain.donki.model.LinkedEvent
//import org.team.b4.cosmicadventures.domain.donki.model.Mpc
import org.team.b4.cosmicadventures.domain.donki.model.News
import org.team.b4.cosmicadventures.domain.donki.repository.DonkiRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class DonkiServiceImpl(
    private val donkiRepository: DonkiRepository,
    private val objectMapper: ObjectMapper
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


    fun saveAllFromApi() {
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        saveFromApi("$BASE_URL/MPC?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", mpcResponseExtractor)
        saveFromApi("$BASE_URL/SEP?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", sepResponseExtractor)
        saveFromApi("$BASE_URL/CME?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", cmeResponseExtractor)
        saveFromApi("$BASE_URL/GST?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", gstResponseExtractor)
        saveFromApi("$BASE_URL/RBE?startDate=$yesterday&endDate=$yesterday&api_key=$API_KEY", rbeResponseExtractor)
    }

    override fun saveFromApi(apiUrl: String, extractor: (JsonNode) -> News) {
        val client = WebClient.create()
        val responseSpec = client.get()
            .uri(apiUrl)
            .retrieve()
        val response = responseSpec.bodyToMono(String::class.java).block()

        if (!response.isNullOrEmpty()) {
            val nodeList = objectMapper.readTree(response)
            val newsList = nodeList.map { node -> extractor(node) }
            donkiRepository.saveAll(newsList) // EntityManager 사용
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
            title = mpcDto.mpcID,
            body = "관측시간은 ${mpcDto.eventTime}, 관련정보 링크는 ${mpcDto.link}입니다."
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
            title = sepDto.sepID,
            body = "관측시간은 ${sepDto.eventTime}, 관련정보 링크는 ${sepDto.link}입니다."
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
            title = cmeDto.cmeID,
            body = "관측시간은 ${cmeDto.startTime}, 카탈로그는 ${cmeDto.catalog} 관련정보 링크는 ${cmeDto.link}입니다."
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
            title = gstDto.gstID,
            body = "관측시간은 ${gstDto.startTime}, 관련정보 링크는 ${gstDto.link}입니다."
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
            title = rbeDto.rbeID,
            body = "관측시간은 ${rbeDto.eventTime}, 관련정보 링크는 ${rbeDto.link}입니다."
        )
    }


}


//    private fun extractMpcFromApiResponse(mpcResponse: String?): List<Mpc> {
//        val nodeList = objectMapper.readTree(mpcResponse)
//        val mpcList = mutableListOf<Mpc>()
//
//        for (node in nodeList) {
////            val instruments: List<Instrument>? = node["instruments"]?.let {
////                objectMapper.readValue(it.toString(), List::class.java) as List<Instrument>
////            }
//
////            val instruments: List<Instrument>
////            if (node["instruments"].toString().equals("null")) {
////                instruments = mutableListOf()
////            } else {
////                instruments = objectMapper.readValue(node["instruments"].toString(), List::class.java) as List<Instrument>
////            }
////
////            var linkedEvents: List<LinkedEvent>
////            if (node["linkedEvents"].toString().equals("null")) {
////                linkedEvents = mutableListOf()
////            } else {
////                linkedEvents = objectMapper.readValue(node["linkedEvents"].toString(), List::class.java) as List<LinkedEvent>
////            }
//
////
//
//            val eventTimeString = node["eventTime"].asText().replace("Z", "")
//            val eventTime = LocalDateTime.parse(eventTimeString)
//
//            val mpc = Mpc(
//                mpcID = node["mpcID"].asText(),
//                eventTime = eventTime,
//                link = node["link"].asText(),
////                instruments = instruments,
////                linkedEvents = linkedEvents,
//            )
//            mpcList.add(mpc)
//        }
//
//        return mpcList
//    }
