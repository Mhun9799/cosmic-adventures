package org.team.b4.cosmicadventures.domain.marsrover.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.marsrover.model.MarsRover


interface MarsRoverRepository : JpaRepository<MarsRover, Long>