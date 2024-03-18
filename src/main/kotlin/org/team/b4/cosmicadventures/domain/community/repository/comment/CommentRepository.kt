package org.team.b4.cosmicadventures.domain.community.repository.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.model.Comment
import org.team.b4.cosmicadventures.domain.user.model.User

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByUser(user: User): List<Comment>
}