package org.team.b4.cosmicadventures.domain.community.service

import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardRequest
import org.team.b4.cosmicadventures.global.security.UserPrincipal

interface BoardService {

    // 게시글 생성
    fun createBoard(
        boardRequest: BoardRequest, userPrincipal: UserPrincipal): BoardDto

    // 게시글 수정
    fun updateBoard(boardId: Long, boardRequest: BoardRequest, userPrincipal: UserPrincipal): BoardDto

    // 게시글 삭제
    fun deleteBoard(boardId: Long, userPrincipal: UserPrincipal): String

    //게시글 목록조회
    fun getListBoard(): List<BoardDto>

    //게시글 단건조회
    fun getBoard(boardId: Long): BoardDto

    // 게시글 좋아요
    fun likeUpBoard(boardId: Long): BoardDto

}
