package org.team.b4.cosmicadventures.global.exception

data class ModelNotFoundException(val model: String, val id: Long? = null) : RuntimeException("$model ${id?.let { id }} 가 존재하지 않습니다.") {
}