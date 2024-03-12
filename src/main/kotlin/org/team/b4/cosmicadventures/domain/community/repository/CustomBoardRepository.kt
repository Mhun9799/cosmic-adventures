package org.team.b4.cosmicadventures.domain.community.repository

import org.team.b4.cosmicadventures.domain.community.model.Board

interface CustomBoardRepository {
    fun getBoardByCreateAt(): List<Board>
    fun getBoardByLikeUp(): List<Board>
}