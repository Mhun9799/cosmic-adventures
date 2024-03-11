package org.team.b4.cosmicadventures.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.user.model.User
import org.team.b4.cosmicadventures.domain.user.model.UserRecentPasswords

interface UserRecentPasswordsRepository : JpaRepository<UserRecentPasswords, Long> {
    fun findTop3ByUserOrderByIdDesc(user: User): List<UserRecentPasswords>
}