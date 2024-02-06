package com.modernlibrary.generativecontentservice.service

import com.modernlibrary.generativecontentservice.client.ChatGptClient
import com.modernlibrary.generativecontentservice.constant.GenerativeContentServiceConstants.Companion.CHAT_GPT_CONTENT_MODERATION_PROMPT_SECTION
import com.modernlibrary.generativecontentservice.model.dtos.GenerateContentRequestDto
import com.modernlibrary.generativecontentservice.model.dtos.GeneratedContentResponseDto
import com.modernlibrary.generativecontentservice.model.dtos.PromptDto
import com.modernlibrary.generativecontentservice.repository.store.PromptStore
import org.springframework.stereotype.Service

@Service
class ChatGptContentGeneratorServiceImpl(
    private val promptStore: PromptStore,
    private val chatGptClient: ChatGptClient,
): ContentGeneratorService {

    override suspend fun generateMultipleContents(requestDtos: List<GenerateContentRequestDto>): List<GeneratedContentResponseDto> {
        val response = mutableListOf<GeneratedContentResponseDto>()
        requestDtos.forEach { request ->
            val promptDto = fetchPrompts(request)
            val generatedContent = chatGptClient.generateContent(promptDto)
            response.add(
                GeneratedContentResponseDto(
                    content = generatedContent,
                    contentTone = request.contentTone,
                    contentDetails = request.contentDetails
                )
            )
        }
        return response
    }

    private suspend fun fetchPrompts(request: GenerateContentRequestDto): PromptDto {
        val promptDto = promptStore.fetchPrompt(request.contentDetails.templateReferenceId)
        mergePromptComponents(promptDto, request)
        return promptDto
    }

    private fun mergePromptComponents(promptDto: PromptDto, request: GenerateContentRequestDto) {
        val promptInferredParams = removeUnusedPromptsPatterns(
            fillBlanksInPrompts(
                promptDto.userDerivedInformation,
                createPromptPropertyMapping(request)
            )
        )
        // System definition is included in client
        promptDto.finalPrompt = promptDto.promptObjective + promptInferredParams +
                CHAT_GPT_CONTENT_MODERATION_PROMPT_SECTION + promptDto.outputStyle
    }

    private fun createPromptPropertyMapping(request: GenerateContentRequestDto): Map<String, String> {
        val propertyValueMap = mutableMapOf<String, String>()
        propertyValueMap["CREATIVITY"] = request.contentTone.creativity.toString()
        propertyValueMap["FORMAL"] = request.contentTone.formal.toString()
        propertyValueMap["HUMOR"] = request.contentTone.humor.toString()
        propertyValueMap["SERIOUS"] = request.contentTone.serious.toString()
        propertyValueMap["NARRATIVE"] = request.contentTone.narrative.toString()
        propertyValueMap["EDUCATIONAL"] = request.contentTone.educational.toString()
        return propertyValueMap
    }

    private fun fillBlanksInPrompts(prompt: String, propertiesToBeFilled: Map<String, String>): String {
        return propertiesToBeFilled.entries.fold(prompt) { temporaryPrompt, (property, value) ->
            val placeholder = "<>!<BLANK ${property.uppercase()} >!<>"
            temporaryPrompt.replace(placeholder, value)
        }
    }

    private fun removeUnusedPromptsPatterns(prompt: String): String {
        val pattern = Regex("""<><BLANK\s\w+>!<>""")
        return pattern.replace(prompt, "")
    }
}
