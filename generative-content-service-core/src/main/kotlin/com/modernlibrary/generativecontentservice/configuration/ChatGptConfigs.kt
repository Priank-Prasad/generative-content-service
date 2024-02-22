package com.modernlibrary.generativecontentservice.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "chat-gpt.default")
data class ChatGptConfigs(
    val token: String,
    val baseUrl: String,
    val enableMockResponse: Boolean,
)
