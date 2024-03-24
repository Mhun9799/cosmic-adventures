package org.team.b4.cosmicadventures.domain.news.service

import com.fasterxml.jackson.databind.JsonNode
import org.team.b4.cosmicadventures.domain.news.model.News

interface EonetService {
    fun saveFromApi(apiUrl: String, extractor: (JsonNode) -> News)
    fun saveAllFromApi()
}