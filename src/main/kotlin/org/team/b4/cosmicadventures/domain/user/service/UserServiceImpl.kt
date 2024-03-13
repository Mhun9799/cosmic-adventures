package org.team.b4.cosmicadventures.domain.user.service



import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.global.security.RefreshToken.model.RefreshToken
import org.team.b4.cosmicadventures.global.exception.ModelNotFoundException
import org.team.b4.cosmicadventures.global.security.jwt.JwtPlugin
import org.team.b4.cosmicadventures.domain.user.dto.request.LoginRequest
import org.team.b4.cosmicadventures.domain.user.dto.request.SignUpRequest
import org.team.b4.cosmicadventures.domain.user.dto.request.UpdateUserPasswordRequest
import org.team.b4.cosmicadventures.domain.user.dto.request.UpdateUserProfileRequest
import org.team.b4.cosmicadventures.domain.user.dto.response.LoginResponse
import org.team.b4.cosmicadventures.domain.user.dto.response.UserResponse
import org.team.b4.cosmicadventures.domain.user.model.*
import org.team.b4.cosmicadventures.global.security.RefreshToken.repository.RefreshTokenRepository
import org.team.b4.cosmicadventures.domain.user.repository.UserRecentPasswordsRepository
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository
import org.team.b4.cosmicadventures.domain.user.emailservice.EmailService
import org.team.b4.cosmicadventures.global.aws.S3Service
import org.team.b4.cosmicadventures.global.exception.InvalidCredentialException
import org.team.b4.cosmicadventures.global.security.UserPrincipal
import java.util.*


@Service
class UserServiceImpl(

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userRecentPasswordsRepository: UserRecentPasswordsRepository,
    private val jwtPlugin: JwtPlugin,
    private val slangFilterService: SlangFilterService,
    private val s3Service: S3Service,
    private val emailService: EmailService,
    private val refreshTokenRepository: RefreshTokenRepository,

) : UserService {

    override fun login(
        request: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse {

        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호를 확인해주세요.")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호를 확인해주세요.")
        }
        if (user.verificationCode != request.verificationCode) {
            throw IllegalArgumentException("이메일 인증 코드가 일치하지 않습니다.")
        }
        // 엑세스 토큰 생성
        val accessToken = user.role?.let {
            jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = it.name
            )
        }
        accessToken?.let { jwtPlugin.removeTokenFromBlacklist(it) }
        // 리프레시 토큰 생성 및 DB에 저장
        val refreshToken = user.role?.let {
            jwtPlugin.generateRefreshToken(
                subject = user.id.toString(),
                email = user.email,
                role = it.name
            )
        }
        refreshTokenRepository.save(RefreshToken(user = user, token = refreshToken.toString()))
        // 쿠키에 엑세스 토큰 추가
        val accessTokenCookie = Cookie("access_token", accessToken)
        accessTokenCookie.path = "/"
        response.addCookie(accessTokenCookie)
        // 헤더에 엑세스 토큰 추가
        response.addHeader("Authorization", "Bearer $accessToken")
        return LoginResponse(
            name = user.name,
        )
    }

    override fun logout(response: HttpServletResponse, request: HttpServletRequest) {
        val accessToken = jwtPlugin.extractAccessTokenFromRequest(request)
        // 쿠키에서 엑세스 토큰 삭제
        jwtPlugin.deleteAccessTokenCookie(response)
        // 블랙리스트에 엑세스 토큰 추가
        jwtPlugin.invalidateToken(accessToken)
    }

    override fun withdrawal(userId: Long) {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is UserPrincipal) {
            val authenticatedId: Long = principal.id
            if (userId != authenticatedId) {
                throw IllegalArgumentException("탈퇴 권한이 없습니다.")
            }
            val user = userRepository.findById(userId)
                .orElseThrow { throw IllegalArgumentException("해당 회원을 찾을 수 없습니다.") }
            user.status = Status.WITHDRAWAL
            userRepository.save(user)
        } else {
            throw IllegalStateException("로그인을 해주세요.")
        }
    }

    override fun updatePassword(
        userId: Long,
        request: UpdateUserPasswordRequest
    ): String {
        // 기존 비밀번호 확인
        val user = userRepository.findById(userId).orElseThrow { ModelNotFoundException("user", userId) }
        if (!passwordEncoder.matches(request.userPassword, user.password)) {
            throw InvalidCredentialException("기존 비밀번호가 일치하지 않습니다.")
        }
        val newPassword = request.userNewPassword
        val newPasswordHash = passwordEncoder.encode(newPassword)
        val recentPasswords = userRecentPasswordsRepository.findTop3ByUserOrderByIdDesc(user)
        if (recentPasswords.any { passwordEncoder.matches(newPassword, it.password) }) {
            throw IllegalArgumentException("최근 3번 사용한 비밀번호는 사용할 수 없습니다.")
        }
        val recentPassword = UserRecentPasswords(newPasswordHash, user)
        userRecentPasswordsRepository.save(recentPassword)
        // 현재 사용자 엔티티에 새로운 비밀번호 설정 및 저장
        user.password = newPasswordHash
        userRepository.save(user)

        return "비밀번호 변경이 완료되었습니다."
    }




    @Transactional
    override fun signUp(
        request: SignUpRequest
    ): UserResponse {
        if (slangFilterService.isCleanText(request.introduction,)) {
            throw IllegalArgumentException("욕설금지🤬🤬🤬")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이메일이 이미 사용중입니다.")
        }
        if (request.password != request.confirmpassword) {
            throw IllegalArgumentException("비밀번호와 확인 비밀번호가 일치하지 않습니다.")
        }
        var uploadedImageStrings: MutableList<String>? = null
        if (!request.isPicsEmpty()) {
            uploadedImageStrings = s3Service.upload(request.profilePic!!, "profile").toMutableList()
        }
        val hashedPassword = passwordEncoder.encode(request.password)
        val user = userRepository.save(request.to().apply {
            password = hashedPassword
            if (uploadedImageStrings != null) {
                profilePicUrl = uploadedImageStrings
            }
        })
        val verificationCode = UUID.randomUUID().toString().substring(0, 6)
        user.verificationCode = verificationCode
        userRepository.save(user)
        emailService.sendVerificationEmail(user.email, verificationCode)
        return UserResponse.from(user)
    }

    @Transactional
    override fun updateUserProfile(
        userId: Long,
        request: UpdateUserProfileRequest
    ): UserResponse {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
        if (userId != authenticatedId) {
            throw IllegalArgumentException("프로필 수정 권한이 없습니다.")
        }
        var uploadedImageStrings: MutableList<String>? = null
        if (!request.isPicsEmpty()) {
            uploadedImageStrings = s3Service.upload(request.profilePic, "user").toMutableList()
        }
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.name = request.name
        user.introduction = request.introduction
        user.tlno = request.tlno
        if (uploadedImageStrings != null) {
            user.profilePicUrl = uploadedImageStrings
        }
        return UserResponse.from(user)
    }





}


