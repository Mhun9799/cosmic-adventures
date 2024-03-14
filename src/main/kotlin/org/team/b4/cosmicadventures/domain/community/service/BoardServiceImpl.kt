package org.team.b4.cosmicadventures.domain.community.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardRequest
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.repository.BoardRepository
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository
import org.team.b4.cosmicadventures.global.aws.S3Service
import org.team.b4.cosmicadventures.global.exception.ModelNotFoundException
import org.team.b4.cosmicadventures.global.exception.UserNotMatchedException
import org.team.b4.cosmicadventures.global.security.UserPrincipal

@Service
@Transactional
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val s3Service: S3Service
) : BoardService {
    // 게시글 목록조회 작성일순으로 오름차순
    override fun getListBoardByCreateAtASc(): List<BoardDto> =
        boardRepository.getBoardByCreateAt().map { BoardDto.from(it) }

    // 게시글 목록조회 좋아요 순
    override fun getListBoardByLikeUp(): List<BoardDto> =
        boardRepository.getBoardByLikeUp().map { BoardDto.from(it) }

    //게시글 단건조회
    override fun getBoard(boardId: Long): BoardDto {
        val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("board", boardId)
        return BoardDto.from(board)
    }

    //게시글 작성
    override fun createBoard(
        boardRequest: BoardRequest,
        userPrincipal: UserPrincipal
    ): BoardDto {
        //S3 에서 이미지 url 받아옴
        val image = s3Service.upload(boardRequest.image!!, "board").toMutableList()
        val users =
            userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val board = boardRepository.save(boardRequest.to(users, image))
        return BoardDto.from(board)
    }

    //게시글 수정
    override fun updateBoard(boardId: Long, boardRequest: BoardRequest, userPrincipal: UserPrincipal): BoardDto {
        val image = s3Service.upload(boardRequest.image!!, "board").toMutableList()
        val users = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        boardRepository.findByIdAndUserId(boardId, userPrincipal.id) ?: throw UserNotMatchedException()
        return BoardDto.from(boardRepository.save(boardRequest.to(users, image)))
    }

    //게시글 삭제
    override fun deleteBoard(boardId: Long, userPrincipal: UserPrincipal): String {
        userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val board = boardRepository.findByIdAndUserId(boardId, userPrincipal.id) ?: throw UserNotMatchedException()
        boardRepository.delete(board)
        return "삭제완료"
    }

    override fun likeUpBoard(boardId: Long): BoardDto {
        val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("board", boardId)
        board.likeUp()
        return BoardDto.from(board)

    }

}