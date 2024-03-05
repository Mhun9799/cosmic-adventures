package org.team.b4.cosmicadventures.domain.eonet.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.eonet.model.NasaEvent


interface NasaEventRepository : JpaRepository<NasaEvent, Long> {
}