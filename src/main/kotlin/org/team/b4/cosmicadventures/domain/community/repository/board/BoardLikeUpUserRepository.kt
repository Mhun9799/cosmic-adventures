package org.team.b4.cosmicadventures.domain.community.repository.board

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.community.model.BoardLikeUpUser

interface BoardLikeUpUserRepository : JpaRepository<BoardLikeUpUser, Long>, CustomBoardLikeUpUserRepository {
}