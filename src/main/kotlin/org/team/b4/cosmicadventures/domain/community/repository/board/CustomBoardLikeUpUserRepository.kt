package org.team.b4.cosmicadventures.domain.community.repository.board

import org.team.b4.cosmicadventures.domain.community.dto.BoardLikeDto
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.model.BoardLikeUpUser
import org.team.b4.cosmicadventures.domain.user.model.User

interface CustomBoardLikeUpUserRepository {
    fun findBoardLikeUser(board: Board, user: User): BoardLikeUpUser?
    fun findByBoardLikeUserList(board: Board): List<BoardLikeUpUser>
}