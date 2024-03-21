package org.team.b4.cosmicadventures.domain.apod.model

import jakarta.persistence.*
import java.io.Serializable
import java.net.URL
import java.time.LocalDate


@Entity
@Table(name = "nasaimages")
class NasaImage(

    @Column
    val title: String,

    @Column(columnDefinition = "TEXT")
    val explanation: String?,

    @Column
    val date: LocalDate,

    @Column
    val hdurl: URL,

    @Column
    val media_type: String,

    @Column
    val service_version: String,

    @Column
    val url: URL

) : Serializable {
    @Id
    @Column(name = "nasaimage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}