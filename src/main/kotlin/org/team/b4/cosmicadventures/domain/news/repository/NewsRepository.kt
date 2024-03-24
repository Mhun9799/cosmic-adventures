package org.team.b4.cosmicadventures.domain.news.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.team.b4.cosmicadventures.domain.news.model.News


interface NewsRepository : JpaRepository<News,Long> {
}