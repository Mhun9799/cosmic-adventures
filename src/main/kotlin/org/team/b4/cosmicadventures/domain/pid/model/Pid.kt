package org.team.b4.cosmicadventures.domain.pid.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.team.b4.cosmicadventures.domain.community.model.Comment

@Entity
class Pid(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val title: String,
    @Column
    val content: String,

    //val image

    //val comment: Comment
)