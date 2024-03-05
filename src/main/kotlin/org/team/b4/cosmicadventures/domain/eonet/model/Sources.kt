package org.team.b4.cosmicadventures.domain.eonet.model

import jakarta.persistence.*

@Entity
@Table(name = "events_sources")
class Sources(

    @Column(name = "sourcess_id", nullable = false)
    val sourceId: String? = null,

    @Column(nullable = false)
    val url: String? = null
) {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}