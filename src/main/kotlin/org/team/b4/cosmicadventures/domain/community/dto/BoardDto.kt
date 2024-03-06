package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.user.model.Profile

data class BoardDto(
    val title: String,
    val content: String,
    val name: Profile?,
    val likeCount: Int
) {
    companion object {
        fun from(board: Board): BoardDto =
            BoardDto(
                title = board.title,
                content = board.content,
                name = board.nickName,
                likeCount = board.likeCount
            )
    }

}
