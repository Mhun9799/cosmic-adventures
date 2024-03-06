package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.user.model.Profile

data class BoardDto(
    val id: Long,
    val title: String,
    val content: String,
    val name: String,
    val likeCount: Int
) {
    companion object {
        fun from(board: Board): BoardDto =
            BoardDto(
                id = board.id!!,
                title = board.title,
                content = board.content,
                name = board.nickName.nickname,
                likeCount = board.likeCount
            )
    }

}
