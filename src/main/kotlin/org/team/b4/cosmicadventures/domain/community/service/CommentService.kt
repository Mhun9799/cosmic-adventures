package org.team.b4.cosmicadventures.domain.community.service

import org.team.b4.cosmicadventures.domain.community.dto.CommentDto
import org.team.b4.cosmicadventures.domain.community.dto.CommentRequest
import org.team.b4.cosmicadventures.global.security.UserPrincipal

interface CommentService {
    fun createComment(boardId: Long, commentRequest: CommentRequest, userPrincipal: UserPrincipal): CommentDto
    fun updateComment(
        boardId: Long, commentId: Long, commentRequest: CommentRequest, userPrincipal: UserPrincipal
    ): CommentDto
    fun deleteComment(commentId: Long, userPrincipal: UserPrincipal): String
//    fun likeUpComment(commentId: Long): CommentDto
}