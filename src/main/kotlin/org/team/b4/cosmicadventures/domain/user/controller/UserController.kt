package org.team.b4.cosmicadventures.domain.user.controller


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.team.b4.cosmicadventures.domain.user.dto.*
import org.team.b4.cosmicadventures.domain.user.service.UserServiceImpl


@RestController
class UserController(
    private val userService: UserServiceImpl
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest : LoginRequest): ResponseEntity<LoginResponse> {
        return  ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest))

    }

    @PostMapping("/signup")
    fun signUp(@RequestBody signupRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signupRequest))
    }


    @PutMapping("/users/{userId}/profile")
    fun updateUserProfile(@PathVariable userId: Long,
                          @RequestBody updateUserProfileRequest: UpdateUserProfileRequest
    ):
            ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userId,updateUserProfileRequest))

    }
}