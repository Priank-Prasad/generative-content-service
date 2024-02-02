package com.modernlibrary.generativecontentservice

import com.modernlibrary.generativecontentservice.configuration.httpclientconfiguration.NettyHttpClientConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing

@SpringBootApplication
@EnableConfigurationProperties(
    value = [NettyHttpClientConfiguration::class]
)
@EnableR2dbcAuditing
open class GenerativeContentServiceApplication

fun main(args: Array<String>) {
    runApplication<GenerativeContentServiceApplication>(*args)
    println("Generative Content Service's application has started")
}