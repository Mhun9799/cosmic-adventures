package org.team.b4.cosmicadventures.global.security.RefreshToken.model

import jakarta.persistence.*
import org.team.b4.cosmicadventures.domain.user.model.User

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @Column(nullable = false, unique = true)
    val token: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}