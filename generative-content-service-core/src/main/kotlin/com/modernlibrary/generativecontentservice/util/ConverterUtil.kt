package com.modernlibrary.generativecontentservice.util

import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalRequest
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalResponse
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GeneratedContentDetailResponse
import com.modernlibrary.contentorchestratorservice.client.enums.ContentType
import com.modernlibrary.generativecontentservice.model.dtos.CreateContentRequestDto
import com.modernlibrary.generativecontentservice.model.dtos.GeneratedContentResponseDto
import com.modernlibrary.generativecontentservice.model.enums.ComponentType
import com.modernlibrary.generativecontentservice.model.exception.GenerativeContentServiceException

class ConverterUtil {

    companion object {

        fun buildCreateContentRequestDto(request: GenerateComponentInternalRequest): List<CreateContentRequestDto> {
            val dtoList = mutableListOf<CreateContentRequestDto>()
            request.generateContentRequest.componentsToGenerate?.forEach { contentRequest ->
                dtoList.add(
                    CreateContentRequestDto(
                        requestId = request.requestId,
                        templateReferenceId = request.templateReferenceId,
                        componentType = ComponentType.findValueFromString(contentRequest.key.name)
                            ?: throw GenerativeContentServiceException("Unknown component type"),
                        componentReferenceId = contentRequest.value.componentReferenceId,
                    )
                )
            }
            return dtoList
        }

        fun buildResponseFromGeneratedContentResponseDto(
            request: GenerateComponentInternalRequest,
            internalResponse: List<GeneratedContentResponseDto>
        ): GenerateComponentInternalResponse {

            return GenerateComponentInternalResponse(
                requestId = request.requestId,
                contentReferenceId = request.contentReferenceId,
                generatedContent = buildGeneratedContentObject(internalResponse)
            )

        }

        private fun buildGeneratedContentObject(
            internalResponse: List<GeneratedContentResponseDto>
        ): List<GeneratedContentDetailResponse> {
            return internalResponse.map { generatedResponseDto ->
                GeneratedContentDetailResponse(
                    content = generatedResponseDto.content,
                    componentReferenceId = generatedResponseDto.contentDetails.componentReferenceId,
                    tone = generatedResponseDto.contentTone,
                    contentType = ContentType.valueOf(generatedResponseDto.contentDetails.componentType.name),
                )
            }
        }
    }
}
