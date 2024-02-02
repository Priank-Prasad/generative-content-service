package com.modernlibrary.generativecontentservice.configuration.webclientfactory

import com.modernlibrary.generativecontentservice.configuration.clientconfiguration.ChatGptClientConfiguration
import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Component
class ChatGptWebClientFactory(
    private val webClientBuilder: WebClient.Builder,
    private val chatGptClientConfiguration: ChatGptClientConfiguration,
    @Qualifier("chatGptHttpClient") val httpClient: HttpClient,
) {

    @Bean
    @Scope("singleton")
    fun defaultWebClientForChatGpt(): WebClient {
        return webClientBuilder.clientConnector(
            ReactorClientHttpConnector(
                httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, chatGptClientConfiguration.connectionTimeoutInMs)
                    .responseTimeout(Duration.ofMillis(chatGptClientConfiguration.responseTimeoutInMs))
            )
        ).build()
    }
}