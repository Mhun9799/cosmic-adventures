package org.team.b4.cosmicadventures.domain.community.dto
import org.team.b4.cosmicadventures.domain.community.model.Board
import java.time.ZonedDateTime


data class BoardDto(
    val id: Long,
    val title: String,
    val content: String,
    val name: String,
    val likeCount: Int,
    val creatAt: ZonedDateTime,
    val image: MutableList<String>?
) {
    companion object {
        fun from(board: Board): BoardDto =
            BoardDto(
                id = board.id!!,
                title = board.title,
                content = board.content,
                name = board.nickName,
                likeCount = board.likeCount,
                creatAt = board.createdAt,
                image = board.image
            )
    }

}
