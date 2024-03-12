package org.team.b4.cosmicadventures.domain.community.dto

import org.springframework.web.multipart.MultipartFile
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.user.model.User


data class BoardRequest(
    val title: String,
    val content: String,
    val image: MutableList<MultipartFile>?
) {
    fun to(user: User): Board {
        return Board(
            title = this.title,
            content = this.content,
            user = user,
            nickName = user.name,
        )
    }
}
