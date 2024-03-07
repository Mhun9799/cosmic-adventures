package org.team.b4.cosmicadventures.domain.donki.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.team.b4.cosmicadventures.domain.donki.dto.request.CmeDto
import org.team.b4.cosmicadventures.domain.donki.dto.request.MpcDto
import org.team.b4.cosmicadventures.domain.donki.dto.request.SepDto
//import org.team.b4.cosmicadventures.domain.donki.model.Instrument
//import org.team.b4.cosmicadventures.domain.donki.model.LinkedEvent
//import org.team.b4.cosmicadventures.domain.donki.model.Mpc
import org.team.b4.cosmicadventures.domain.donki.model.News
import org.team.b4.cosmicadventures.domain.donki.repository.DonkiRepository
import java.time.LocalDateTime

@Service
class DonkiServiceImpl(
    private val donkiRepository: DonkiRepository,
    private val objectMapper: ObjectMapper
) : DonkiService {

    override fun saveMpcFromApi() {
        val client = WebClient.create()
        val responseSpec = client.get()
            .uri("https://api.nasa.gov/DONKI/MPC?startDate=2024-01-01&endDate=2024-03-05&api_key=CF7Jy0Yjlf3vGkZLVpS2ZF3nYWmatYUVfcZMxhuR")
            .retrieve()
        val mpcResponse = responseSpec.bodyToMono(String::class.java).block()

        val newsList = extractMpcFromApiResponse(mpcResponse)
        donkiRepository.saveAll(newsList) // EntityManager 사용
    }

    private fun extractMpcFromApiResponse(mpcResponse: String?): List<News> {
        val nodeList = objectMapper.readTree(mpcResponse)
        val newsList = mutableListOf<News>()

        for (node in nodeList) {
            val eventTimeString = node["eventTime"].asText().replace("Z", "")
            val eventTime = LocalDateTime.parse(eventTimeString)

            val mpcDto = MpcDto(
                mpcID = node["mpcID"].asText(),
                eventTime = eventTime,
                link = node["link"].asText(),
//                instruments = null,
//                linkedEvents = null,
            )

            val news = News(
                title = mpcDto.mpcID,
                body = "관측시간은 ${mpcDto.eventTime}, 관련정보 링크는 ${mpcDto.link}입니다."
            )
            newsList.add(news)
        }

        return newsList
    }

    override fun saveSepFromApi() {
        val client = WebClient.create()
        val responseSpec = client.get()
            .uri("https://api.nasa.gov/DONKI/SEP?startDate=2024-01-01&endDate=2024-01-02&api_key=CF7Jy0Yjlf3vGkZLVpS2ZF3nYWmatYUVfcZMxhuR")
            .retrieve()
        val sepResponse = responseSpec.bodyToMono(String::class.java).block()

        val newsList = extractSepFromApiResponse(sepResponse)
        donkiRepository.saveAll(newsList) // EntityManager 사용
    }

    private fun extractSepFromApiResponse(sepResponse: String?): List<News> {
        val nodeList = objectMapper.readTree(sepResponse)
        val newsList = mutableListOf<News>()

        for (node in nodeList) {
            val eventTimeString = node["eventTime"].asText().replace("Z", "")
            val eventTime = LocalDateTime.parse(eventTimeString)

            val sepDto = SepDto(
                sepID = node["sepID"].asText(),
                eventTime = eventTime,
                link = node["link"].asText(),
            )


            val news = News(
                title = sepDto.sepID,
                body = "관측시간은 ${sepDto.eventTime}, 관련정보 링크는 ${sepDto.link}입니다."
            )
            newsList.add(news)
        }

        return newsList
    }

    override fun saveCmeFromApi() {
        val client = WebClient.create()
        val responseSpec = client.get()
            .uri("https://api.nasa.gov/DONKI/CME?startDate=2024-03-06&endDate=2024-03-06&api_key=CF7Jy0Yjlf3vGkZLVpS2ZF3nYWmatYUVfcZMxhuR")
            .retrieve()
        val cmeResponse = responseSpec.bodyToMono(String::class.java).block()

        val newsList = extractCmeFromApiResponse(cmeResponse)
        donkiRepository.saveAll(newsList) // EntityManager 사용
    }

    private fun extractCmeFromApiResponse(cmeResponse: String?): List<News> {
        val nodeList = objectMapper.readTree(cmeResponse)
        val newsList = mutableListOf<News>()

        for (node in nodeList) {
            val startTimeString = node["startTime"].asText().replace("Z", "")
            val startTime = LocalDateTime.parse(startTimeString)

            val cmeDto = CmeDto(
                cmeID = node["activityID"].asText(),
                startTime = startTime,
                catalog = node["catalog"].asText(),
                link = node["link"].asText(),
            )


            val news = News(
                title = cmeDto.cmeID,
                body = "관측시간은 ${cmeDto.startTime}, 카탈로그는 ${cmeDto.catalog} 관련정보 링크는 ${cmeDto.link}입니다."
            )
            newsList.add(news)
        }

        return newsList
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
}