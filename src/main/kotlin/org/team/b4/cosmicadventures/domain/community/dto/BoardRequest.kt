package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Board


data class BoardRequest(
    val title: String,
    val content: String
) {
    fun to(): Board {
        return Board(
            title = this.title,
            content = this.content
        )
    }
}
