package com.modernlibrary.generativecontentservice.repository.store

import com.modernlibrary.generativecontentservice.model.dtos.CreateContentRequestDto
import com.modernlibrary.generativecontentservice.model.dtos.GeneratedContentDetailsDto
import com.modernlibrary.generativecontentservice.model.entity.GeneratedContent
import com.modernlibrary.generativecontentservice.model.enums.ContentStatus
import com.modernlibrary.generativecontentservice.repository.GeneratedContentRepository
import com.modernlibrary.generativecontentservice.util.RandomNumberGeneratorUtil.Companion.generateRandomNumberWithPrefix
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class GeneratedContentStore(
    private val generatedContentRepository: GeneratedContentRepository,
) {

    suspend fun createContent(request: CreateContentRequestDto): GeneratedContentDetailsDto {
        val content = generatedContentRepository.save(
            GeneratedContent(
                requestId = request.requestId,
                componentReferenceId = request.componentReferenceId,
                templateReferenceId = request.templateReferenceId,
                generativeReferenceId = generateRandomNumberWithPrefix("GRC"),
                componentType = request.componentType,
                status = ContentStatus.RECEIVED,
            )
        ).awaitFirst()
        return convertEntityToDto(content)
    }

    private fun convertEntityToDto(content: GeneratedContent): GeneratedContentDetailsDto {
        return GeneratedContentDetailsDto(
            requestId = content.requestId,
            componentReferenceId = content.componentReferenceId,
            generativeReferenceId = content.generativeReferenceId,
            componentType = content.componentType,
            status =  content.status,
            templateReferenceId = content.templateReferenceId
        )
    }
}
