package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.model.Comment
import org.team.b4.cosmicadventures.domain.user.model.User

data class CommentRequest(
    val content: String
) {
    fun to(board: Board, user: User): Comment =
        Comment(
            content = this.content,
            board = board,
            name = user.profile,
            user = user
        )
}