package org.team.b4.cosmicadventures.specialDay.dto


data class TestRequest (
    val serviceKey: String,
    val pageNo: Int,
    val numOfRows: Int,
    val solYear: Int,
    val solMonth: Int
)