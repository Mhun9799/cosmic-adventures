package org.team.b4.cosmicadventures.domain.user.service



import jakarta.transaction.InvalidTransactionException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.global.exception.ModelNotFoundException
import org.team.b4.cosmicadventures.global.security.jwt.JwtPlugin
import org.team.b4.cosmicadventures.domain.user.dto.*
import org.team.b4.cosmicadventures.domain.user.model.Profile
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.domain.user.model.UserRole
import org.team.b4.cosmicadventures.domain.user.model.toResponse
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (user.role.name != request.role || !passwordEncoder.matches(request.password, user.password)) {
            throw InvalidTransactionException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            )
        )
    }

    @Transactional
    override fun signUp(request: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                profile = Profile(
                    nickname = request.nickname
                ),
                role = when (request.role) {
                    UserRole.USER.name -> UserRole.USER
                    UserRole.ADMIN.name -> UserRole.ADMIN
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()

    }

    @Transactional
    override fun updateUserProfile(userId: Long, request: UpdateUserProfileRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.profile = Profile(
            nickname = request.nickname
        )

        return userRepository.save(user).toResponse()
    }


}
