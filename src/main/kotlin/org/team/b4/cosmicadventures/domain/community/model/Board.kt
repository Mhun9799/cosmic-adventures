package org.team.b4.cosmicadventures.domain.community.model


import jakarta.persistence.*
import org.team.b4.cosmicadventures.domain.user.model.Profile
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.global.model.BaseEntity

@Entity
@Table(name = "board")
class Board(
    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,

    @Column(name = "name", nullable = false)
    val nickName: Profile? = null,

    @Column(name = "count")
    val likeCount: Int = 0,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: User? = null
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}