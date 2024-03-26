package org.team.b4.cosmicadventures.domain.user.service


import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.team.b4.cosmicadventures.domain.community.dto.BoardDto
import org.team.b4.cosmicadventures.domain.community.dto.BoardLikeDto
import org.team.b4.cosmicadventures.domain.community.dto.CommentDto
import org.team.b4.cosmicadventures.domain.community.dto.LikedBoardDto
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.user.dto.request.LoginRequest
import org.team.b4.cosmicadventures.domain.user.dto.request.SignUpRequest
import org.team.b4.cosmicadventures.domain.user.dto.request.UpdateUserPasswordRequest
import org.team.b4.cosmicadventures.domain.user.dto.request.UpdateUserProfileRequest
import org.team.b4.cosmicadventures.domain.user.dto.response.LoginResponse
import org.team.b4.cosmicadventures.domain.user.dto.response.UserResponse

interface UserService {

        fun signUp(request: SignUpRequest): UserResponse
        fun updateUserProfile(userId: Long, request: UpdateUserProfileRequest): UserResponse
        fun login(request: LoginRequest,response: HttpServletResponse): LoginResponse
        fun updatePassword(request: UpdateUserPasswordRequest)
        fun logout(response: HttpServletResponse, request: HttpServletRequest)
        fun withdrawal(userId: Long)
        fun getUserProfile(userId: Long):UserResponse

        fun sendPasswordResetCode(email: String, phoneNumber: String): Boolean
        fun temporaryPassword(email: String, phoneNumber: String, passwordCode: String): String

        fun getUserBoards(authenticatedId: Long): List<BoardDto>
        fun getUserComments(authenticatedId: Long): List<CommentDto>

        fun getLikedBoardsByUserId(authenticatedId: Long): List<LikedBoardDto>

        fun getUserBoardDetails(authenticatedId: Long, boardId: Long): BoardDto
}
