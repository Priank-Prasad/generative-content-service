package com.modernlibrary.generativecontentservice

import com.modernlibrary.generativecontentservice.configuration.ChatGptConfigs
import com.modernlibrary.generativecontentservice.configuration.clientconfiguration.ChatGptClientConfiguration
import com.modernlibrary.generativecontentservice.configuration.httpclientconfiguration.NettyHttpClientConfiguration
import com.modernlibrary.generativecontentservice.configuration.webclientfactory.ChatGptWebClientFactory
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties(
    value = [NettyHttpClientConfiguration::class, ChatGptConfigs::class, ChatGptClientConfiguration::class]
)
@EnableR2dbcAuditing
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
open class GenerativeContentServiceApplication

fun main(args: Array<String>) {
    runApplication<GenerativeContentServiceApplication>(*args)
    println("Generative Content Service's application has started")
}