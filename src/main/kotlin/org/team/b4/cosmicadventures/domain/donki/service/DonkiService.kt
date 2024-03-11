package org.team.b4.cosmicadventures.domain.donki.service

import com.fasterxml.jackson.databind.JsonNode
import org.team.b4.cosmicadventures.domain.donki.model.News

interface DonkiService {
    fun saveFromApi(apiUrl: String, extractor: (JsonNode) -> News)
}