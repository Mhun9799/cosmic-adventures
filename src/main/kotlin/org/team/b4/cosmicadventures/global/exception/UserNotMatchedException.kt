package org.team.b4.cosmicadventures.global.exception

data class UserNotMatchedException(
    override val message: String = "작성자만 접근 가능합니다."
) : RuntimeException()