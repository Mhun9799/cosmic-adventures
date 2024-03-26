package org.team.b4.cosmicadventures.domain.community.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardLikeDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardRequest
import org.team.b4.cosmicadventures.domain.community.dto.BoardRetrieveDto
import org.team.b4.cosmicadventures.domain.community.service.BoardService
import org.team.b4.cosmicadventures.domain.user.model.QUser.user
import org.team.b4.cosmicadventures.global.security.UserPrincipal
import java.net.URI

@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {
    @Operation(summary = "게시글 목록 조회 작성일 기준")
    @GetMapping("/creates")
    fun getListBoardByCreateAT(
    ): ResponseEntity<List<BoardDto>> =
        ResponseEntity.ok().body(boardService.getListBoardByCreateAtASc())
    @Operation(summary = "게시글 목록 조회 좋아요 순")
    @GetMapping("/likes")
    fun getListBoardByLikeUp(
    ): ResponseEntity<List<BoardDto>> =
        ResponseEntity.ok().body(boardService.getListBoardByLikeUp())

    @Operation(summary = "게시글 단건조회")
    @GetMapping("/{boardId}")
    fun getBoard(
        @PathVariable boardId: Long
    ): ResponseEntity<BoardDto> =
        ResponseEntity.ok().body(boardService.getBoard(boardId))

    @Operation(summary = "게시글 작성")
    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createBoard(
        @ModelAttribute boardRequest: BoardRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<BoardDto> {
        val imageList = boardRequest.image ?: mutableListOf()
        return ResponseEntity.created(URI.create("/"))
            .body(boardService.createBoard(boardRequest, userPrincipal, imageList))
    }
    @Operation(summary = "게시글 수정")
    @PutMapping(
        "/{boardId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateBoard(
        @PathVariable boardId: Long,
        @ModelAttribute boardRequest: BoardRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<BoardDto> {
        val imageList = boardRequest.image ?: mutableListOf()
        return ResponseEntity.ok().body(boardService.updateBoard(boardId, boardRequest, userPrincipal, imageList))
    }
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{boardId}")
    fun deleteBoard(
        @PathVariable boardId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): String =
        boardService.deleteBoard(boardId, userPrincipal)

    @Operation(summary = "게시글 좋아요")
    @PostMapping("/{boardId}")
    fun likeUpBoard(
        @PathVariable boardId: Long,
        @AuthenticationPrincipal user: UserPrincipal
    ): ResponseEntity<BoardRetrieveDto> {
        return ResponseEntity.ok().body(boardService.likeUpBoard(boardId, user))
    }

    @Operation(summary = "게시글 좋아요한 사용자 목록")
    @GetMapping("/{boardId}/like-users")
    fun getLikeUser(
        @PathVariable boardId: Long,
        @AuthenticationPrincipal user: UserPrincipal
    ): ResponseEntity<List<BoardLikeDto>> {
        return ResponseEntity.ok().body(boardService.getLikeUser(boardId, user))
    }
}