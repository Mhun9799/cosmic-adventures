package org.team.b4.cosmicadventures.domain.marsrover.model

import jakarta.persistence.*

@Entity
@Table(name = "marsrover")
class MarsRover(

    @Column(name = "img_src")
    val imgSrc: String,

    @Column(name = "earth_date")
    val earthDate: String,

    @Column(name = "rover_name")
    val roverName: String,

    @Column(name = "camera_name")
    val cameraName: String

) {
    @Id
    @Column(name = "marsrover_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}