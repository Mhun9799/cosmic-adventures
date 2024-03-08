package org.team.b4.cosmicadventures.global.exception

data class BusinessLogicException(
    override val message: String? = "올바르지 않은 방식입니다."
) : RuntimeException()