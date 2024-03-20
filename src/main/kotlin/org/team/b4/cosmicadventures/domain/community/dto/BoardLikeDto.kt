package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.BoardLikeUpUser

data class BoardLikeDto(
    val id: Long,
    val name: String
) {
    companion object {
        fun from(boardLikeUpUser: BoardLikeUpUser): BoardLikeDto = BoardLikeDto(
            id = boardLikeUpUser.user.id!!,
            name = boardLikeUpUser.user.name
        )
    }
}
