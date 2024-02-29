package org.team.b4.cosmicadventures.domain.user.service


import org.team.b4.cosmicadventures.domain.user.dto.*

interface UserService {

        fun signUp(request: SignUpRequest): UserResponse

        fun updateUserProfile(userId: Long, request: UpdateUserProfileRequest): UserResponse

        fun login(request: LoginRequest): LoginResponse

}