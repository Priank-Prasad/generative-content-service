package com.modernlibrary.generativecontentservice.model.client

data class ChatGptRequest(
    val model: String,
    val response_format: String,
    val message: List<ChatGptMessage>
)

data class ChatGptMessage(
    val role: String,
    val content: String,
)
