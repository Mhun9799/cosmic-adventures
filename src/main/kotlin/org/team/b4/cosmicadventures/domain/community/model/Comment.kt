package org.team.b4.cosmicadventures.domain.community.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.team.b4.cosmicadventures.domain.community.dto.CommentRequest
import org.team.b4.cosmicadventures.domain.user.model.Profile
import org.team.b4.cosmicadventures.domain.user.model.User

@Entity
@Table(name = "comment")
class Comment(
    @Column
    var content: String,

    @Column
    val name: Profile,

    @Column(name = "count")
    var likeCount: Int = 0,

    @JoinColumn(name = "board_id")
    @JsonIgnore
    @ManyToOne
    val board: Board,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun changedComment(commentRequest: CommentRequest) {
        this.content = commentRequest.content
    }
    fun likeUp() {
        likeCount += 1
    }
}