package com.modernlibrary.generativecontentservice.configuration.httpclientconfiguration

import com.modernlibrary.generativecontentservice.configuration.clientconfiguration.ChatGptClientConfiguration
import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.util.UriTemplate
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration


@Component
class ChatGptHttpClientConfiguration {

    @Bean("chatGptHttpClient")
    fun chatGptHttpClient(
        @Qualifier("defaultConnectionProvider") connectionProvider: ConnectionProvider,
        chatGptClientConfiguration: ChatGptClientConfiguration,
        @Qualifier("chatGptUriTemplates") uriTemplates: Set<UriTemplate>
    ) : HttpClient {

        return HttpClient.create(connectionProvider).option(
            ChannelOption.CONNECT_TIMEOUT_MILLIS,
            chatGptClientConfiguration.connectionTimeoutInMs
        ).responseTimeout(
            Duration.ofMillis(chatGptClientConfiguration.responseTimeoutInMs)
        ).metrics(true) {
                uri -> uriTemplates.stream().filter { uriTemplates -> uriTemplates.matches(uri) }
            .findFirst().map { uriTemplate -> uriTemplate.toString() }
            .orElse(uri)
        }
    }

    @Bean("chatGptUriTemplates")
    fun contentGeneratorServiceUriTemplates(): Set<UriTemplate> {
        return setOf()
    }
}