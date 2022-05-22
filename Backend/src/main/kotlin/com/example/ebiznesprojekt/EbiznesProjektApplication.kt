package com.example.ebiznesprojekt

import com.example.ebiznesprojekt.configuration.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class EbiznesProjektApplication

fun main(args: Array<String>) {
	runApplication<EbiznesProjektApplication>(*args)
}
