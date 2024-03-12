package org.team.b4.cosmicadventures.domain.community.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.community.model.Board

interface BoardRepository : JpaRepository<Board, Long>, CustomBoardRepository {
    fun findByIdAndUserId(boardId: Long, userId: Long?): Board?
}