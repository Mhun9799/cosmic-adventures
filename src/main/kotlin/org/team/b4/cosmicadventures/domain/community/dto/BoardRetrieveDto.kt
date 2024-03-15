package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.model.BoardLikeUpUser

data class BoardRetrieveDto(
    val boardId: Long,
    val userId: Long,
    val name: String,
    val like: Int,
    val likeStatus: Boolean
) {
    companion object {
        fun from(board: Board, boardLikeUpUser: BoardLikeUpUser): BoardRetrieveDto =
            BoardRetrieveDto(
                boardId = board.id!!,
                userId = board.user.id!!,
                name = board.nickName,
                like = board.likeCount,
                likeStatus = boardLikeUpUser.like
            )
    }
}