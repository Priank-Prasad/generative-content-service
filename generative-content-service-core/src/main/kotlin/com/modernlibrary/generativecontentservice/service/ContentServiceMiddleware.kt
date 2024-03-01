package com.modernlibrary.generativecontentservice.service

import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalRequest
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalResponse
import com.modernlibrary.generativecontentservice.model.dtos.GenerateContentRequestDto
import com.modernlibrary.generativecontentservice.model.exception.GenerativeContentServiceException
import com.modernlibrary.generativecontentservice.repository.store.GeneratedContentStore
import com.modernlibrary.generativecontentservice.util.ConverterUtil.Companion.buildCreateContentRequestDto
import com.modernlibrary.generativecontentservice.util.ConverterUtil.Companion.buildResponseFromGeneratedContentResponseDto
import org.springframework.stereotype.Service

@Service
class ContentServiceMiddleware(
    private val generatedContentStore: GeneratedContentStore,
    private val chatGptContentGeneratorServiceImpl: ChatGptContentGeneratorServiceImpl
) {

    suspend fun createContent(request: GenerateComponentInternalRequest): GenerateComponentInternalResponse {
        val contentCreationRequestList = buildCreateContentRequestDto(request)
        val contentDetailsDtoList = generatedContentStore.createContent(contentCreationRequestList)
        val generateContentRequestDtoList = contentCreationRequestList.map { contentCreationRequest ->
            GenerateContentRequestDto(
                contentDetails = contentDetailsDtoList.find { it.componentReferenceId == contentCreationRequest.componentReferenceId } ?: throw GenerativeContentServiceException("No matching content is generated, please check"),
                contentTone = contentCreationRequest.tone,
                userDetails = request.userDetails,
                configs = contentCreationRequest.contentConfig
            )
        }
        val response = chatGptContentGeneratorServiceImpl.generateMultipleContents(generateContentRequestDtoList)
        return buildResponseFromGeneratedContentResponseDto(request, response)
    }
}
