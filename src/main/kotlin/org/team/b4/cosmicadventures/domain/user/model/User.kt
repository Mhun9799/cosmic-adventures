package org.team.b4.cosmicadventures.domain.user.model



import jakarta.persistence.*
import org.team.b4.cosmicadventures.domain.user.dto.UserResponse


@Entity
@Table(name = "app_user")
class User(

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,



    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}


fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        nickname = profile.nickname,
        email = email,
        role = role.name,
    )
}