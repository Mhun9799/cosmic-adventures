package org.team.b4.cosmicadventures.domain.donki.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.donki.model.News


interface DonkiRepository : JpaRepository<News,Long> {
}