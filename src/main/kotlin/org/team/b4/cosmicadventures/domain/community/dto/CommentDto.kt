package org.team.b4.cosmicadventures.domain.community.dto

import org.team.b4.cosmicadventures.domain.community.model.Comment


data class CommentDto(
    val id: Long,
    val content: String,
    val name: String,
    val likeCount: Int,
) {
    companion object {
        fun from(comment: Comment): CommentDto {
            return CommentDto(
                id = comment.id!!,
                name = comment.name,
                content = comment.content,
                likeCount = comment.likeCount,
            )
        }
    }
}