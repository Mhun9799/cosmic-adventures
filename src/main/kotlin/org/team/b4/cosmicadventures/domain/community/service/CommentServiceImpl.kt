package org.team.b4.cosmicadventures.domain.community.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.domain.community.dto.CommentDto
import org.team.b4.cosmicadventures.domain.community.dto.CommentRequest
import org.team.b4.cosmicadventures.domain.community.repository.board.BoardRepository
import org.team.b4.cosmicadventures.domain.community.repository.comment.CommentRepository
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository
import org.team.b4.cosmicadventures.global.exception.ModelNotFoundException
import org.team.b4.cosmicadventures.global.security.UserPrincipal

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
): CommentService {

    // 댓글 작성
    override fun createComment(
        boardId: Long,
        commentRequest: CommentRequest,
        userPrincipal: UserPrincipal
    ): CommentDto {
        val user =
            userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("board", boardId)
        val comment = commentRepository.save(commentRequest.to(board, user))
        return CommentDto.from(comment)
    }

    // 댓글 수정
    override fun updateComment(
        boardId: Long,
        commentId: Long,
        commentRequest: CommentRequest,
        userPrincipal: UserPrincipal
    ): CommentDto {
        userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)
        comment.changedComment(commentRequest)
        return CommentDto.from(comment)
    }

    // 댓글 삭제
    override fun deleteComment(commentId: Long, userPrincipal: UserPrincipal): String {
        userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("user", userPrincipal.id)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)
        commentRepository.delete(comment)
        return "댓글 삭제완료"
    }

//    override fun likeUpComment(commentId: Long): CommentDto {
//        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)
//        comment.likeUp()
//        return CommentDto.from(comment)
//    }
}