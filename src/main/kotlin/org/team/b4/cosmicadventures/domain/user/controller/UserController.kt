package org.team.b4.cosmicadventures.domain.user.controller


import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardLikeDto
import org.team.b4.cosmicadventures.domain.community.dto.CommentDto
import org.team.b4.cosmicadventures.domain.community.dto.LikedBoardDto
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.user.dto.request.*
import org.team.b4.cosmicadventures.domain.user.dto.response.LoginResponse
import org.team.b4.cosmicadventures.domain.user.dto.response.UserResponse
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository
import org.team.b4.cosmicadventures.domain.user.service.UserServiceImpl
import org.team.b4.cosmicadventures.global.security.UserPrincipal
import org.team.b4.cosmicadventures.global.security.jwt.JwtPlugin



@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val jwtPlugin: JwtPlugin,
    private val userService: UserServiceImpl,
    private val userRepository: UserRepository,

) {

    @Operation(summary = "회원가입")
    @PostMapping("/signup",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun signUp(
        @Valid
        @ModelAttribute signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequest))
    }


    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody loginRequest: LoginRequest,response: HttpServletResponse
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest,response))
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    fun logout(
        response: HttpServletResponse,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        userService.logout(response, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("로그아웃이 성공적으로 처리되었습니다.")
    }


    @Operation(summary = "프로필 조회 관리자전용")
    @GetMapping("/{userId}/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getUserProfile(@PathVariable userId: Long):ResponseEntity<UserResponse>{
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity.ok(userProfile)
    }


    @Operation(summary = "프로필 조회")
    @GetMapping("/profile")
    fun getUserProfile(): ResponseEntity<UserResponse> {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인을 부터")
        val user = userRepository.findById(authenticatedId)
            .orElseThrow { IllegalArgumentException("프로필 조회 권한이 없습니다.") }
        val userProfile = userService.getUserProfile(authenticatedId)
        return ResponseEntity.ok(userProfile)
    }

    @Operation(summary = "프로필 수정 관리자 전용")
    @PutMapping("/{userId}/profile-edit",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun updateUserProfile(
        @Valid
        @PathVariable userId: Long,
        @ModelAttribute updateUserProfileRequest: UpdateUserProfileRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userId, updateUserProfileRequest))
    }

    @Transactional
    @Operation(summary = "프로필 수정")
    @PutMapping("/profile-edit",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUserProfile(
        @ModelAttribute updateUserProfileRequest: UpdateUserProfileRequest
    ): ResponseEntity<UserResponse> {
        val authenticatedUserId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인부터")
        // 사용자 인증 후, 해당 사용자의 프로필을 수정하는 작업 수행
        val updatedUserProfile = userService.updateUserProfile(authenticatedUserId, updateUserProfileRequest)
        return ResponseEntity.ok(updatedUserProfile)
    }

    @Operation(summary = "비밀번호 수정")
    @PutMapping("/my-password")
    fun updatePassword(
        @Valid
        @RequestBody request: UpdateUserPasswordRequest
    ): ResponseEntity<String> {
        userService.updatePassword(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("비밀번호 수정 완료되었습니다.")
    }



    @Operation(summary = "블랙리스트토큰 테스트")
    @GetMapping("/tokens")
    fun getBlacklistedTokens(): ResponseEntity<Set<String>> {
        val blacklistedTokens = jwtPlugin.getBlacklistedTokens()
        return ResponseEntity
            .ok(blacklistedTokens)
    }


    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/withdrawal/{userId}")
    fun withdrawal(@PathVariable userId: Long): ResponseEntity<String> {
        userService.withdrawal(userId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("회원 탈퇴가 성공적으로 처리되었습니다.")
    }


    @Operation(summary = "비밀번호찾기인증코드")
    @PostMapping("/send-password-code")
    fun resetPassword(@RequestBody request: PasswordResetRequest): ResponseEntity<String> {
        userService.sendPasswordResetCode(request.email, request.phoneNumber)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("핸드폰번호로 문자가 전송되었습니다.")
    }

    @Operation(summary = "인증코드로임시비밀번호")
    @PostMapping("/temporary-password")
    fun temporaryPassword(@RequestBody request: TemporaryPasswordRequest): ResponseEntity<String> {
        userService.temporaryPassword(request.email, request.phoneNumber, request.code)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("이메일로 임시비밀번로가 전송되었습니다.")
    }


    @Operation(summary = "나의 게시글")
    @GetMapping("/boards")
    fun getUserBoards(): ResponseEntity<List<BoardDto>> {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인부터")
        val boards = userService.getUserBoards(authenticatedId)
        return ResponseEntity.ok(boards)
    }

    @Operation(summary = "나의 게시글 상세 정보 조회")
    @GetMapping("/boards/{boardId}")
    fun getUserBoardDetails(@PathVariable boardId: Long): ResponseEntity<BoardDto> {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인부터")
        val boardDetails = userService.getUserBoardDetails(authenticatedId, boardId)
        return ResponseEntity.ok(boardDetails)
    }

    @Operation(summary = "나의 댓글")
    @GetMapping("/comments")
    fun getUserComments(): ResponseEntity<List<CommentDto>> {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인부터")
        val comments = userService.getUserComments(authenticatedId)
        return ResponseEntity.ok(comments)
    }

    @Operation(summary = "나의 좋아요")
    @GetMapping("/liked-boards")
    fun getLikedBoards(): ResponseEntity<List<LikedBoardDto>> {
        val authenticatedId = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인부터")
        val likedBoards = userService.getLikedBoardsByUserId(authenticatedId)
        return ResponseEntity.ok(likedBoards)
    }
}