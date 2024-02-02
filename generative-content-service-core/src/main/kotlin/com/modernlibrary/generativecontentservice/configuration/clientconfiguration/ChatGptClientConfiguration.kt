package com.modernlibrary.generativecontentservice.configuration.clientconfiguration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "chat-gpt-client")
data class ChatGptClientConfiguration(
    val responseTimeoutInMs: Long,
    val connectionTimeoutInMs: Int,
)