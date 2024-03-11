package org.team.b4.cosmicadventures.domain.community.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.community.model.Board
import org.team.b4.cosmicadventures.domain.community.model.Comment

interface CommentRepository : JpaRepository<Comment, Long> {
}