package org.team.b4.cosmicadventures.global.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.team.b4.cosmicadventures.global.security.jwt.JwtPlugin

@Component
class TokenRefreshInterceptor(
    private val jwtPlugin: JwtPlugin
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val refreshToken = extractRefreshTokenFromRequest(request)
        if (refreshToken != null) {
            try {
                // 리프레시 토큰 검증
                val refreshTokenClaims = jwtPlugin.validateToken(refreshToken).getOrThrow().body

                // 여기에서 리프레시 토큰을 이용하여 새로운 엑세스 토큰 생성
                val newAccessToken = jwtPlugin.generateAccessToken(
                    subject = refreshTokenClaims.subject,
                    email = refreshTokenClaims["email"].toString(),
                    role = refreshTokenClaims["role"].toString()
                )
                // 새로운 엑세스 토큰을 응답 헤더에 추가
                response.addHeader("Authorization", "Bearer $newAccessToken")

                jwtPlugin.deleteExpiredRefreshToken(refreshToken)

            } catch (e: Exception) {
                // 리프레시 토큰이 유효하지 않으면 무시
            }
        }
        return true
    }
    private fun extractRefreshTokenFromRequest(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 다음에 오는 부분이 리프레시 토큰
            return authorizationHeader.substring(7)
        }
        return null
    }
}