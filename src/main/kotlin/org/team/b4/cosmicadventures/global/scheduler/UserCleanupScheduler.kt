package org.team.b4.cosmicadventures.global.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.team.b4.cosmicadventures.domain.community.repository.board.BoardRepository
import org.team.b4.cosmicadventures.domain.community.repository.comment.CommentRepository
import org.team.b4.cosmicadventures.domain.user.model.Status
import org.team.b4.cosmicadventures.domain.user.repository.UserRepository
import java.time.LocalDate


@Component
class UserCleanupScheduler(
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
) {
    // 매일 자정에 실행되어 90일이 지난 WAIT 상태인 사용자를 삭제 및 게시글과 댓글도 삭제
    @Scheduled(cron = "0 0 0 * * ?")
    fun cleanupWaitUsers() {
        val thresholdDate = LocalDate.now().minusDays(90)
        val wITHDRAWALUsers = userRepository.findByStatus(Status.WITHDRAWAL)
        wITHDRAWALUsers.forEach { user ->
            val createdAtDate = user.createdAt.toLocalDate()
            if (createdAtDate.isBefore(thresholdDate)) {
                val userPosts = boardRepository.findByUser(user)
                userPosts.forEach { boardRepository.delete(it) }
                val userComments = commentRepository.findByUser(user)
                userComments.forEach { commentRepository.delete(it) }
                userRepository.delete(user)
            }
        }
    }
}