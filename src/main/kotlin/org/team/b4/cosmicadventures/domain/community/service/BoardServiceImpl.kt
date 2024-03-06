package org.team.b4.cosmicadventures.domain.community.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardRequest
import org.team.b4.cosmicadventures.domain.community.repository.BoardRepository
import org.team.b4.cosmicadventures.global.exception.ModelNotFoundException
import org.team.b4.cosmicadventures.global.exception.UserNotMatchedException
import org.team.b4.cosmicadventures.global.security.UserPrincipal

@Service
@Transactional
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val userRepository: BoardRepository
) : BoardService {
    // 게시글 목록조회
    override fun getListBoard(): List<BoardDto> =
        boardRepository.findAll().map { BoardDto.from(it) }

    //게시글 단건조회
    override fun getBoard(boardId: Long): BoardDto {
        val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("board", boardId)
        return BoardDto.from(board)
    }

    //게시글 작성
    override fun createBoard(boardRequest: BoardRequest, userPrincipal: UserPrincipal): BoardDto {
        userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val board = boardRepository.save(boardRequest.to())
        return BoardDto.from(board)
    }

    //게시글 수정
    override fun updateBoard(boardId: Long, boardRequest: BoardRequest, userPrincipal: UserPrincipal): BoardDto {
        userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        boardRepository.findByIdAndUserId(boardId, userPrincipal.id) ?: throw UserNotMatchedException()
        return BoardDto.from(boardRepository.save(boardRequest.to()))
    }

    //게시글 삭제
    override fun deleteBoard(boardId: Long, userPrincipal: UserPrincipal): String {
        userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val board = boardRepository.findByIdAndUserId(boardId, userPrincipal.id) ?: throw UserNotMatchedException()
        boardRepository.delete(board)
        return "삭제완료"
    }

}