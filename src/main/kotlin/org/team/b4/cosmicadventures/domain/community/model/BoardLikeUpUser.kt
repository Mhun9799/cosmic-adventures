package org.team.b4.cosmicadventures.domain.community.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.team.b4.cosmicadventures.domain.user.model.User

@Entity
@Table(name = "board_like_user")
class BoardLikeUpUser(
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    @JsonIgnore
    val board: Board,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "like_status")
    var like: Boolean = false
    fun likeTrue() {
        like = true
    }
    fun likeFalse() {
        like = false
    }
}