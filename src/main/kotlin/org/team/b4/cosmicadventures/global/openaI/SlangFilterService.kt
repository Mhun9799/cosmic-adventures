package org.team.b4.cosmicadventures.global.openaI


import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class SlangFilterService(
    private val webClient: WebClient,
    @Value("\${openai.api.key}") private var openaiApiKey: String
) {
    private val logger = LoggerFactory.getLogger(SlangFilterService::class.java)

    fun isCleanText(userInput: String): Boolean {
        val apiUrl = "https://api.openai.com/v1/chat/completions"
        val requestBody = mapOf(
            "model" to "gpt-3.5-turbo",
            "messages" to listOf(
                mapOf(
                    "role" to "system",
                    "content" to "비속어가 있으면 무조건 true 비속어가 없으면 무조건 false 로 대답해"
                ),
                mapOf("role" to "user", "content" to userInput)
            ),
            "temperature" to 0.7
        )

        try {
            val responseEntity = webClient.post()
                .uri(apiUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $openaiApiKey")
                .bodyValue(requestBody)
                .retrieve()
                .toEntity(String::class.java)
                .block()
            val statusCode = responseEntity?.statusCode
            logger.info("API Response Status Code: {}", statusCode)
            // 상태 코드 확인 및 처리
            return if (statusCode?.is2xxSuccessful == true) {
                val responseBody = responseEntity.body
                logger.info("API Response Body: {}", responseBody)
                // responseBody에서 contains_inappropriate_language가 true이면 false 반환
                extractContainsInappropriateLanguage(responseBody)
            } else {
                // 상태 코드가 성공이 아닌 경우
                false
            }
        } catch (ex: Exception) {
            logger.error("Exception during API call", ex)
            throw ex
        }
    }

    private fun extractContainsInappropriateLanguage(responseBody: String?): Boolean {
        val json = ObjectMapper().readTree(responseBody ?: "")

        // "choices" 배열에서 "content" 값을 가져오고 이 값을 기준으로 판단
        val contentNode = json.path("choices").firstOrNull()?.path("message")?.path("content")
        return if (contentNode != null && contentNode.isTextual) {
            contentNode.asText().equals("true", ignoreCase = true)
        } else {
            // "content" 필드가 없거나 부적절한 형식인 경우
            logger.error("Invalid or missing 'content' field in JSON response")
            false
        }
    }

}


