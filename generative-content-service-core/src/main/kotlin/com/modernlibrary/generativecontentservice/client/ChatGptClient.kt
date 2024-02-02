package com.modernlibrary.generativecontentservice.client

import com.modernlibrary.generativecontentservice.configuration.ChatGptConfigs
import com.modernlibrary.generativecontentservice.configuration.webclientfactory.ChatGptWebClientFactory
import com.modernlibrary.generativecontentservice.model.client.ChatGptMessage
import com.modernlibrary.generativecontentservice.model.client.ChatGptRequest
import com.modernlibrary.generativecontentservice.model.client.ChatGptResponse
import com.modernlibrary.generativecontentservice.model.dtos.PromptDto
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class ChatGptClient(
    private val baseClient: BaseClient,
    private val chatGptConfigs: ChatGptConfigs,
    private val chatGptWebClientFactory: ChatGptWebClientFactory,
) {
    companion object {
        private const val CHAT_COMPLETION_ENDPOINT = "/chat/completions"
    }

    // Need to store request response in a DB
    suspend fun generateContent(promptDto: PromptDto) : String {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer ${chatGptConfigs.token}")
        val response = baseClient.post(
            chatGptWebClientFactory.defaultWebClientForChatGpt(),
            createRequest(promptDto),
            ChatGptResponse::class.java,
            chatGptConfigs.baseUrl + CHAT_COMPLETION_ENDPOINT,
            headers
        )
        return if (response.choices[0].finish_reason == "stop") {
            response.choices[0].message.content
        } else {
            // Need to check what can be done here
            ""
        }
    }

    private fun createRequest(promptDto: PromptDto): ChatGptRequest {
        // response_format is hardcoded, so that response is always in json format
        return ChatGptRequest(
            model = promptDto.model,
            response_format = "{ \"type\": \"json_object\" }",
            message = listOf(
                ChatGptMessage(role = "system", content = promptDto.systemRole),
                ChatGptMessage(role = "user", content = promptDto.finalPrompt ?: promptDto.promptObjective)
            )
        )
    }
}