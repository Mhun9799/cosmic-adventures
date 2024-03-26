package org.team.b4.cosmicadventures.domain.exoplanet.model

import jakarta.persistence.*

@Entity
@Table(name = "planets")
class Exoplanet(

    @Column(name = "pl_name")
    val planetName: String,

    @Column(name = "pl_letter")
    val planetLetter: String?,

    @Column(name = "hostname")
    val hostname: String,

    @Column(name = "hd_name")
    val hdName: String?,

    @Column(name = "hip_name")
    val hipName: String?,

    @Column(name = "tic_id")
    val ticId: String?,

    @Column(name = "gaia_id")
    val gaiaId: String?,

    @Column(name = "default_flag")
    val defaultFlag: String?,

    @Column(name = "pl_refname")
    val plRefname: String?,

    @Column(name = "sy_refname")
    val syRefname: String?,

    @Column(name = "disc_pubdate")
    val discPubdate: String?,

    @Column(name = "disc_year")
    val discYear: Int?,

    @Column(name = "discoverymethod")
    val discoveryMethod: String?,

    @Column(name = "disc_locale")
    val discLocale: String?,

    @Column(name = "disc_facility")
    val discFacility: String?,

    @Column(name = "disc_instrument")
    val discInstrument: String?,

    @Column(name = "disc_telescope")
    val discTelescope: String?,

    @Column(name = "disc_refname")
    val discRefname: String?,

    @Column(name = "ra")
    val ra: Double?,

    @Column(name = "rastr")
    val rastr: String?,

    @Column(name = "dec")
    val dec: Double?,

    @Column(name = "decstr")
    val decstr: String?,

    @Column(name = "glon")
    val glon: Double?,

    @Column(name = "glat")
    val glat: Double?,

    @Column(name = "elon")
    val elon: Double?,

    @Column(name = "elat")
    val elat: Double?,

    @Column(name = "pl_orbper")
    val plOrbper: Double?,

    @Column(name = "pl_orbpererr1")
    val plOrbperErr1: Double?,

    @Column(name = "pl_orbpererr2")
    val plOrbperErr2: Double?,

    @Column(name = "pl_orbperlim")
    val plOrbperLim: Int?


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}