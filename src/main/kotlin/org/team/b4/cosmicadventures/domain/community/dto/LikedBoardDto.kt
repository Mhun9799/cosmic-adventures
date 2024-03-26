package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Board

data class LikedBoardDto(
    val id: Long,
    val title: String,
    val content: String,
    val name: String,
    val likeCount: Int,
    val image: MutableList<String>?
) {
    companion object {
        fun from(board: Board): LikedBoardDto =
            LikedBoardDto(
                id = board.id!!,
                title = board.title,
                content = board.content,
                name = board.nickName,
                likeCount = board.likeCount,
                image = board.image
            )
    }

}