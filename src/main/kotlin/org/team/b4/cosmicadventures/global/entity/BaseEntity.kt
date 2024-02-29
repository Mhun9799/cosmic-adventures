package org.team.b4.cosmicadventures.global.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @Column(name = "created_At")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_At")
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
}