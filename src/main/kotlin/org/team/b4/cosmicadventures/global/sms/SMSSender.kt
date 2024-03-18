package org.team.b4.cosmicadventures.global.sms

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class SMSSender(
    @Value("\${infobip.api.key}") private val apiKey: String
) {

    private val infobipUrl = "https://434x66.api.infobip.com/sms/2/text/advanced"
    private val senderNumber = "821083513333"
    private val webClient = WebClient.create()

    fun sendSMS(phoneNumber: String, message: String) {
        // HTTP 요청 헤더 설정
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "App $apiKey")

        // HTTP 요청 바디 설정
        val requestBody = mapOf(
            "messages" to listOf(
                mapOf(
                    "from" to senderNumber,
                    "destinations" to listOf(
                        mapOf("to" to phoneNumber)
                    ),
                    "text" to message
                )
            )
        )

        // HTTP 요청 보내기
        webClient.post()
            .uri(infobipUrl)
            .headers { it.addAll(headers) }
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String::class.java)
            .subscribe { response ->
                // 콘솔에 응답 출력
                println("SMS 전송 결과: $response")
            }
    }
}