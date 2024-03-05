package org.team.b4.cosmicadventures.domain.eonet.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "events_geomtries")
class Geometries(
    @Column(nullable = false)
    val date: String? = null,

    @Column(nullable = false)
    val type: String? = null,

    @Column(nullable = false)
    val coordinates: Pair<Double, Double>? = null
) {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}