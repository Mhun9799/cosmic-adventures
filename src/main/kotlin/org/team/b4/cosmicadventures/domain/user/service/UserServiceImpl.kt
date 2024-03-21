package org.team.b4.cosmicadventures.domain.user.service


import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.team.b4.cosmicadventures.global.openaI.SlangFilterService
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
import org.team.b4.cosmicadventures.global.emailservice.EmailService
import org.team.b4.cosmicadventures.global.sms.SMSSender
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
    private val smsSender: SMSSender,

    ) : UserService {

    override fun login(
        request: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호를 확인해주세요.")
        if (user.status == Status.WITHDRAWAL) {
            throw IllegalArgumentException("해당 계정은 탈퇴 처리되었습니다.")
        }
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
        // 쿠키에 리프레쉬 토큰 추가
        val refreshTokenCookie = Cookie("refresh_token", refreshToken)
        refreshTokenCookie.path = "/"
        response.addCookie(refreshTokenCookie)
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
        val authenticatedId = (principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인을 해주세요.")
        if (userId != authenticatedId) {
            throw IllegalArgumentException("탈퇴 권한이 없습니다.")
        }
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("해당 회원을 찾을 수 없습니다.") }
        user.status = Status.WITHDRAWAL
        userRepository.save(user)
    }

    @Transactional
    override fun updatePassword(request: UpdateUserPasswordRequest) {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = (authentication?.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인 부터 ")
        // 기존 비밀번호 확인
        val user = userRepository.findById(userId)
            .orElseThrow { ModelNotFoundException("user", userId) }
        if (!passwordEncoder.matches(request.userPassword, user.password)) {
            throw InvalidCredentialException("기존 비밀번호가 일치하지 않습니다.")
        }
        val newPasswordHash = passwordEncoder.encode(request.userNewPassword)
        val recentPasswords = userRecentPasswordsRepository.findTop3ByUserOrderByIdDesc(user)
        if (recentPasswords.any { passwordEncoder.matches(request.userNewPassword, it.password) }) {
            throw IllegalArgumentException("최근 3번 사용한 비밀번호는 사용할 수 없습니다.")
        }
        val recentPassword = UserRecentPasswords(newPasswordHash, user)
        userRecentPasswordsRepository.save(recentPassword)
        user.password = newPasswordHash
        userRepository.save(user)
    }


    @Transactional
    override fun signUp(request: SignUpRequest): UserResponse {
        if (slangFilterService.isCleanText(request.introduction)) {
            throw IllegalArgumentException("욕설금지🤬🤬🤬")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이메일이 이미 사용중입니다.")
        }
        if (request.password != request.confirmpassword) {
            throw IllegalArgumentException("비밀번호와 확인 비밀번호가 일치하지 않습니다.")
        }
        // 프로필 사진 업로드 처리
        val uploadedImageStrings = if (request.profilePicUrl != null && request.profilePicUrl.isNotEmpty()) {
            s3Service.upload(request.profilePicUrl!!, "profile").toMutableList()
        } else {
            mutableListOf("https://imgur.com/S8jQ6wN") // 기본 이미지 URL로 대체
        }
        // 비밀번호 해싱
        val hashedPassword = passwordEncoder.encode(request.password)
        // 사용자 정보 생성
        val user = User(
            role = Role.USER,
            name = request.name,
            email = request.email,
            password = hashedPassword,
            introduction = request.introduction,
            tlno = request.tlno,
            status = Status.NORMAL
        )
        user.profilePicUrl = uploadedImageStrings
        // 사용자 정보 저장
        val savedUser = userRepository.save(user)
        // 이메일 인증 코드 생성 및 전송
        val verificationCode = UUID.randomUUID().toString().substring(0, 6)
        savedUser.verificationCode = verificationCode
        userRepository.save(savedUser)
        emailService.sendVerificationEmail(savedUser.email, verificationCode)
        return UserResponse.from(savedUser)
    }

    override fun getUserProfile(userId: Long): UserResponse {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인을 부터")
        if (userId != authenticatedId) {
            throw IllegalArgumentException("프로필 조회 권한이 없습니다.")
        }
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("해당 사용자를 찾을 수 없습니다.") }
        return UserResponse.from(user)
    }

    @Transactional
    override fun updateUserProfile(
        userId: Long,
        request: UpdateUserProfileRequest
    ): UserResponse {
        val authenticatedId: Long = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.id
            ?: throw IllegalStateException("로그인을 부터")
        if (userId != authenticatedId) {
            throw IllegalArgumentException("프로필 수정 권한이 없습니다.")
        }
        val uploadedImageStrings = if (request.profilePicUrl != null && request.profilePicUrl!!.isNotEmpty()) {
            s3Service.upload(request.profilePicUrl!!, "profile").toMutableList()
        } else {
            mutableListOf("https://imgur.com/S8jQ6wN")
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

    override fun sendPasswordResetCode(email: String, phoneNumber: String): Boolean {
        val user = userRepository.findByEmailAndTlno(email, phoneNumber)
            ?: throw IllegalArgumentException("이메일 혹은 핸드폰번호가 일치하지 않습니다.")
        val passwordCode = UUID.randomUUID().toString().substring(0, 6)
        val internationalPhoneNumber = "82" + phoneNumber.replace("-", "")
        val message = "인증코드🗝️ $passwordCode\n 코드를 이용하여 임시비밀번호를 받으세용"
        smsSender.sendSMS(internationalPhoneNumber, message)
        userRepository.save(user.apply { this.passwordCode = passwordCode })
        return true
    }

    override fun temporaryPassword(email: String, phoneNumber: String, code: String): String {
        val user = userRepository.findByEmailAndTlno(email, phoneNumber)
            ?: throw IllegalArgumentException("유효하지 않은 인증 코드입니다.")
        if (user.passwordCode != code) {
            throw IllegalArgumentException("유효하지 않은 인증 코드입니다.")
        }
        val passwordLength = 10
        val specials = "!@#$%^&*("
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val specialChar = specials.random()
        val lowerChar = chars.filter { it.isLowerCase() }.random()
        val upperChar = chars.filter { it.isUpperCase() }.random()
        val digitChar = chars.filter { it.isDigit() }.random()
        val passwordChars = buildString {
            append(specialChar)
            append(lowerChar)
            append(upperChar)
            append(digitChar)
            repeat(passwordLength - 4) {
                append(chars.random())
            }
        }
        val savedUser = userRepository.save(user.apply {
            verificationCode = UUID.randomUUID().toString().substring(0, 6)
            password = passwordEncoder.encode(passwordChars)
        })
        savedUser.verificationCode?.let { emailService.sendVerificationEmail(savedUser.email, it, passwordChars) }
        return passwordChars
    }
}

