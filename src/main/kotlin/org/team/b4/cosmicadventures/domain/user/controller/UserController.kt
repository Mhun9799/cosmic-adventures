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
import org.springframework.web.bind.annotation.*
import org.team.b4.cosmicadventures.domain.user.dto.request.*
import org.team.b4.cosmicadventures.domain.user.dto.response.LoginResponse
import org.team.b4.cosmicadventures.domain.user.dto.response.UserResponse
import org.team.b4.cosmicadventures.domain.user.service.UserServiceImpl
import org.team.b4.cosmicadventures.global.security.UserPrincipal
import org.team.b4.cosmicadventures.global.security.jwt.JwtPlugin


@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val jwtPlugin: JwtPlugin,
    private val userService: UserServiceImpl
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


    @Operation(summary = "프로필 조회")
    @GetMapping("/{userId}/profile")
    fun getUserProfile(@PathVariable userId: Long):ResponseEntity<UserResponse>{
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity.ok(userProfile)
    }

    @Operation(summary = "프로필 수정")
    @PutMapping("/{userId}/profile-edit",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUserProfile(
        @Valid
        @PathVariable userId: Long,
        @ModelAttribute updateUserProfileRequest: UpdateUserProfileRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userId, updateUserProfileRequest))
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
}