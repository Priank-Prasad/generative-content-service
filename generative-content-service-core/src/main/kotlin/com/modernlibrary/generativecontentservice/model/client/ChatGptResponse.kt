package com.modernlibrary.generativecontentservice.model.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatGptResponse(
    val id: String,
    val created: Long,
    val model: String,
    val system_fingerprint: String,
    val choices: List<ChatGptChoice>,
    val usage: ChatGptUsage,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatGptUsage(
    val prompt_tokens: Long,
    val completion_tokens: Long,
    val total_tokens: Long,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatGptChoice(
    val index: Long,
    val message: ChatGptMessage,
    val finish_reason: String,
)
