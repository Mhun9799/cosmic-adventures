package org.team.b4.cosmicadventures.domain.user.model


import jakarta.persistence.*
import org.team.b4.cosmicadventures.global.security.RefreshToken.model.RefreshToken
import org.team.b4.cosmicadventures.global.StringMutableListConverter
import org.team.b4.cosmicadventures.global.model.BaseEntity


@Entity
@Table(name = "app_user")
class User(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "tlno")
    var tlno: String,

    @Column(name = "profile_pic_url")
    @Convert(converter = StringMutableListConverter::class)
    var profilePicUrl: MutableList<String> = mutableListOf("https://imgur.com/S8jQ6wN"),

    @Column(name = "verification_code")
    var verificationCode: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var userRecentPasswords: MutableList<UserRecentPasswords> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val refreshTokens: MutableList<RefreshToken> = mutableListOf()


) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}
