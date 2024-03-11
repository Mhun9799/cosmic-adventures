package org.team.b4.cosmicadventures.domain.user.model

import jakarta.persistence.*
import org.team.b4.cosmicadventures.global.model.BaseEntity

@Entity
@Table(name = "user_recent_passwords")
class UserRecentPasswords (

    @Column(name = "user_password", nullable = false)
    var password: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    ) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}