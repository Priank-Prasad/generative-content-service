package com.modernlibrary.generativecontentservice.configuration.httpclientconfiguration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "netty-http-client.default")
data class NettyHttpClientConfiguration(
    val maxConnections: Int,
    val maxConnectionIdleTimeInMs: Long,
    val evictIntervalTimeInMs: Long
)
