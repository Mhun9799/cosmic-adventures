package org.team.b4.cosmicadventures.domain.community.model


import com.querydsl.core.types.dsl.Wildcard.count
import jakarta.persistence.*
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.global.StringMutableListConverter
import org.team.b4.cosmicadventures.global.model.BaseEntity

@Entity
@Table(name = "board")
class Board(
    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,

    @Column(name = "name" )
    var nickName: String,

    @Column(name = "count")
    var likeCount: Int = 0,

    @Column(name = "image")
    @Convert(converter = StringMutableListConverter::class)
    val image: MutableList<String>? = null,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: User
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun likeUp() {
        likeCount += 1
    }
    fun likeDown() {
        likeCount -= 1
    }
}