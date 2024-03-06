package org.team.b4.cosmicadventures.domain.community.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.community.dto.CommentDto
import org.team.b4.cosmicadventures.domain.community.dto.CommentRequest
import org.team.b4.cosmicadventures.domain.community.service.CommentService
import org.team.b4.cosmicadventures.global.security.UserPrincipal
import java.net.URI

@RestController
@RequestMapping("/boards/comments")
class CommentController(
    private val commentService: CommentService
) {
    @Operation(summary = "댓글 작성")
    @PostMapping
    fun createComment(
        @RequestParam boardId: Long,
        @RequestBody commentRequest: CommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentDto> =
        ResponseEntity.created(URI.create("/")).body(commentService.createComment(boardId, commentRequest, userPrincipal))

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    fun updateComment(
        @RequestParam boardId: Long,
        @PathVariable commentId: Long,
        @RequestBody commentRequest: CommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentDto> =
        ResponseEntity.ok().body(commentService.updateComment(boardId, commentId, commentRequest, userPrincipal))

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): String =
        commentService.deleteComment(commentId, userPrincipal)
    @Operation(summary = "댓글 좋아요")
    @PostMapping("/{commentId}")
    fun likeUpComment(
        @PathVariable commentId: Long
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.ok().body(commentService.likeUpComment(commentId))
    }
}