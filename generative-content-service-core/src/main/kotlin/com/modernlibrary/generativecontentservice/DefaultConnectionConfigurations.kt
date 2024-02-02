package com.modernlibrary.generativecontentservice

import com.modernlibrary.generativecontentservice.configuration.httpclientconfiguration.NettyHttpClientConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Component
class DefaultConnectionConfigurations {

    @Bean("defaultConnectionProvider")
    fun connectionPool(nettyHttpClientConfiguration: NettyHttpClientConfiguration): ConnectionProvider {
        return ConnectionProvider.builder("netty-connection-provider")
            .maxConnections(nettyHttpClientConfiguration.maxConnections)
            .metrics(true)
            .maxIdleTime(Duration.ofMillis(nettyHttpClientConfiguration.maxConnectionIdleTimeInMs))
            .evictInBackground(Duration.ofMillis(nettyHttpClientConfiguration.evictIntervalTimeInMs))
            .build()
    }
}