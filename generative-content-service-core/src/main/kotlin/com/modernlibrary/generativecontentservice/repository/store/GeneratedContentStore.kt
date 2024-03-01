package com.modernlibrary.generativecontentservice.repository.store

import com.modernlibrary.generativecontentservice.model.dtos.CreateContentRequestDto
import com.modernlibrary.generativecontentservice.model.dtos.GeneratedContentDetailsDto
import com.modernlibrary.generativecontentservice.model.entity.GeneratedContent
import com.modernlibrary.generativecontentservice.model.enums.ContentStatus
import com.modernlibrary.generativecontentservice.repository.GeneratedContentRepository
import com.modernlibrary.generativecontentservice.util.RandomNumberGeneratorUtil.Companion.generateRandomNumberWithPrefix
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.stereotype.Component

@Component
class GeneratedContentStore(
    private val generatedContentRepository: GeneratedContentRepository,
) {

    suspend fun createContent(request: List<CreateContentRequestDto>): List<GeneratedContentDetailsDto> {
        val content = generatedContentRepository.saveAll(
            request.map {
                GeneratedContent(
                    requestId = it.requestId,
                    componentReferenceId = it.componentReferenceId,
                    templateReferenceId = it.templateReferenceId,
                    generativeReferenceId = generateRandomNumberWithPrefix("GRC"),
                    componentType = it.componentType,
                    status = ContentStatus.RECEIVED,
                )
            }
        ).collectList().awaitLast()
        return convertEntityToDto(content)
    }

    private fun convertEntityToDto(contentList: List<GeneratedContent>): List<GeneratedContentDetailsDto> {
        return contentList.map {
            GeneratedContentDetailsDto(
                requestId = it.requestId,
                componentReferenceId = it.componentReferenceId,
                generativeReferenceId = it.generativeReferenceId,
                componentType = it.componentType,
                status = it.status,
                templateReferenceId = it.templateReferenceId
            )
        }
    }
}
