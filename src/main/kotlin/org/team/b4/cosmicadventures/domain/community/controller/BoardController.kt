package org.team.b4.cosmicadventures.domain.community.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    fun getListBoard(
    ): ResponseEntity<List<BoardDto>> =
        ResponseEntity.ok().body(boardService.getListBoard())

    @Operation(summary = "게시글 단건조회")
    @GetMapping("/{boardId}")
    fun getBoard(
        @PathVariable boardId: Long
    ): ResponseEntity<BoardDto> =
        ResponseEntity.ok().body(boardService.getBoard(boardId))

    @Operation(summary = "게시글 작성")
    @PostMapping
    fun createBoard(
        @RequestBody boardRequest: BoardRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<BoardDto> =
        ResponseEntity.created(URI.create("/")).body(boardService.createBoard(boardRequest, userPrincipal))

    @Operation(summary = "게시글 수정")
    @PutMapping("{boardId}")
    fun updateBoard(
        @PathVariable boardId: Long,
        @RequestBody boardRequest: BoardRequest,
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
}