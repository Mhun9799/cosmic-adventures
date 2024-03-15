package org.team.b4.cosmicadventures.domain.community.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardRequest
import org.team.b4.cosmicadventures.domain.community.service.BoardService
import org.team.b4.cosmicadventures.global.security.UserPrincipal
import java.net.URI

@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {
    @Operation(summary = "게시글 목록 조회 작성일 기준")
    @GetMapping
    fun getListBoardByCreateAT(
    ): ResponseEntity<List<BoardDto>> =
        ResponseEntity.ok().body(boardService.getListBoardByCreateAtASc())
    @Operation(summary = "게시글 목록 조회 좋아요 순")
    @GetMapping("/like")
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
    ): ResponseEntity<BoardDto> =
        ResponseEntity.created(URI.create("/")).body(boardService.createBoard(boardRequest, userPrincipal))

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
    ): ResponseEntity<BoardDto> =
        ResponseEntity.ok().body(boardService.updateBoard(boardId, boardRequest, userPrincipal))

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
        @PathVariable boardId: Long
    ): ResponseEntity<BoardDto> {
        return ResponseEntity.ok().body(boardService.likeUpBoard(boardId))
    }
}