package org.team.b4.cosmicadventures.domain.community.repository.board

import org.springframework.stereotype.Repository
import org.team.b4.cosmicadventures.domain.community.dto.BoardLikeDto
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.model.BoardLikeUpUser
import org.team.b4.cosmicadventures.domain.community.model.QBoard.board
import org.team.b4.cosmicadventures.domain.community.model.QBoardLikeUpUser
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.global.querydsl.QueryDslSupport

@Repository
class BoardLikeUpUserRepositoryImpl : CustomBoardLikeUpUserRepository, QueryDslSupport() {
    private val boardLikeUpUser = QBoardLikeUpUser.boardLikeUpUser

    // 게시글에서 로그인한 유저 정보를 찿는 함수
    override fun findBoardLikeUser(board: Board, user: User): BoardLikeUpUser? {
        return queryFactory.selectFrom(boardLikeUpUser)
            .where(
                boardLikeUpUser.board.eq(board)
                    .and(boardLikeUpUser.user.eq(user))
            ).fetchOne()
    }

    override fun findByBoardLikeUserList(board: Board): List<BoardLikeUpUser> {
        return queryFactory.selectFrom(boardLikeUpUser)
            .where(
                boardLikeUpUser.like.eq(true)
                    .and(boardLikeUpUser.board.eq(board))
            ).fetch()
    }
}