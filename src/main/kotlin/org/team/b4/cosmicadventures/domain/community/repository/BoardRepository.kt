package org.team.b4.cosmicadventures.domain.community.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.user.model.User

interface BoardRepository : JpaRepository<Board, Long> {
    fun findByIdAndUserId(boardId: Long, userId: Long?): Board?
    fun findByUser(user: User): List<Board>
}