package org.team.b4.cosmicadventures.domain.apod.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.apod.model.NasaImage

interface NasaImageRepository : JpaRepository<NasaImage, Long>