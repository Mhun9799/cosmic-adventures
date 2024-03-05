package org.team.b4.cosmicadventures


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching


@EnableCaching
@SpringBootApplication
class CosmicAdventuresApplication

fun main(args: Array<String>) {
	runApplication<CosmicAdventuresApplication>(*args)
}

