package org.team.b4.cosmicadventures.domain.user.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.oauth2.domain.entity.OAuth2Provider
import org.team.b4.cosmicadventures.domain.user.model.Status
import org.team.b4.cosmicadventures.domain.user.model.User

interface UserRepository:JpaRepository<User, Long> {

    fun findByProviderAndProviderId(provider: OAuth2Provider, toString: String): User?
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email:String):User?
    fun findByStatus(status: Status): List<User>
    fun findByEmailAndTlno(email: String, tlno: String): User?


}