package org.team.b4.cosmicadventures.specialDay.service

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.team.b4.cosmicadventures.specialDay.dto.TestRequest


@Service
class TestService {

    private val restClient = RestClient.create()
    val result = restClient.get()
        .uri("")
        .accept(MediaType
        .APPLICATION_JSON)
        .retrieve()
        .body()

    fun getTestUrl(request: TestRequest) {

    }
}
