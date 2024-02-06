package com.modernlibrary.generativecontentservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = arrayOf(HibernateJpaAutoConfiguration::class))
open class Database

fun main(args: Array<String>) {
    runApplication<Database>(*args)
}