package com.modernlibrary.generativecontentservice.repository.store

import com.modernlibrary.generativecontentservice.model.dtos.PromptDto
import com.modernlibrary.generativecontentservice.model.entity.Prompt
import com.modernlibrary.generativecontentservice.model.exception.GenerativeContentServiceException
import com.modernlibrary.generativecontentservice.repository.PromptRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component

@Component
class PromptStore(
    private val promptRepository: PromptRepository,
) {

    suspend fun fetchPrompt(templateReferenceId: String): PromptDto {
        val prompt = promptRepository.findByTemplateReferenceId(templateReferenceId).awaitFirstOrNull()
            ?: throw GenerativeContentServiceException("Template reference id is not registered, please onboard the prompt")
        return convertEntityToDto(prompt)
    }

    private fun convertEntityToDto(prompt: Prompt): PromptDto {
        return PromptDto(
            templateReferenceId = prompt.templateReferenceId,
            systemRole = prompt.systemRole,
            promptObjective = prompt.promptObjective,
            model = prompt.model,
            outputStyle = prompt.outputStyle,
            userDerivedInformation = prompt.userDerivedInformation,
        )
    }
}
