package com.modernlibrary.generativecontentservice.service

import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalRequest
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalResponse
import com.modernlibrary.contentorchestratorservice.client.enums.ContentType
import com.modernlibrary.contentorchestratorservice.client.model.ContentTone
import com.modernlibrary.contentorchestratorservice.client.model.GenerateContentRequest
import com.modernlibrary.generativecontentservice.constant.GenerativeContentServiceConstants.Companion.DEFAULT_TONE_DELTA
import com.modernlibrary.generativecontentservice.model.dtos.GenerateContentRequestDto
import com.modernlibrary.generativecontentservice.model.dtos.GeneratedContentDetailsDto
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

    // Create Content object
    // Build response
    // Request will contain only 1 request for each content type.
    suspend fun createContent(request: GenerateComponentInternalRequest): GenerateComponentInternalResponse {
        val contentDetailsDto = mutableListOf<GeneratedContentDetailsDto>()
        buildCreateContentRequestDto(request).forEach { requestDto ->
            contentDetailsDto.add(generatedContentStore.createContent(requestDto))
        }

        // Choose different tones.
        val response = chatGptContentGeneratorServiceImpl.generateMultipleContents(
            createGenerateContentRequestDto(contentDetailsDto, request)
        )

        return buildResponseFromGeneratedContentResponseDto(request, response)
    }

    private fun createGenerateContentRequestDto(
        contentDetailsList: List<GeneratedContentDetailsDto>, request: GenerateComponentInternalRequest
    ): List<GenerateContentRequestDto> {
        return contentDetailsList.flatMap { contentDetail ->
            val allTones = createAvailableTones(request.generateContentRequest)
            allTones.map { tone ->
                GenerateContentRequestDto(
                    contentDetails = contentDetail,
                    contentTone = tone,
                    userDetails = request.generateContentRequest.userDetails,
                    configs = request.generateContentRequest.componentsToGenerate
                        ?.get(ContentType.valueOf(contentDetail.componentType.name))
                        ?: throw GenerativeContentServiceException("Content Configuration not present")
                )
            }
        }
    }

// Need to remove after testing
//    private fun createGenerateContentRequestDto(
//        contentDetailsList: List<GeneratedContentDetailsDto>, request: GenerateComponentInternalRequest
//    ): List<GenerateContentRequestDto> {
//        val generateContentDtoList = mutableListOf<GenerateContentRequestDto>()
//
//        contentDetailsList.forEach { contentDetail ->
//            val allTones = createAvailableTones(request.generateContentRequest)
//            allTones.forEach { tone ->
//                generateContentDtoList.add(
//                    GenerateContentRequestDto(
//                        contentDetails = contentDetail,
//                        contentTone = tone,
//                        userDetails = request.generateContentRequest.userDetails,
//                        configs = request.generateContentRequest.componentsToGenerate
//                            ?.get(ContentType.valueOf(contentDetail.componentType.name))
//                            ?: throw GenerativeContentServiceException("Content Configuration not present"),
//                    ))
//            }
//        }
//        return generateContentDtoList
//    }


    private fun createAvailableTones(generateContentRequest: GenerateContentRequest): List<ContentTone> {
        val allTones = mutableListOf(generateContentRequest.tone)
        generateContentRequest.userDetails.averageToneOfFollowings?.let { allTones.add(it) }
        generateContentRequest.userDetails.averageToneOfFollowers?.let { allTones.add(it) }
        generateContentRequest.userDetails.userAverageTone?.let { allTones.add(it) }
        allTones.add(generateToneWithDelta(generateContentRequest.tone, DEFAULT_TONE_DELTA))
        allTones.add(generateToneWithDelta(generateContentRequest.tone, -1*DEFAULT_TONE_DELTA))
        return allTones.take(3)
    }

    private fun generateToneWithDelta(tone: ContentTone, delta: Int): ContentTone {
        fun adjustToneValue(value: Float): Float {
            return when {
                (value + delta) < 100 && (value + delta) > 0 -> value + delta
                else -> value
            }
        }

        return ContentTone(
            creativity = adjustToneValue(tone.creativity),
            formal = adjustToneValue(tone.formal),
            humor = adjustToneValue(tone.humor),
            serious = adjustToneValue(tone.serious),
            narrative = adjustToneValue(tone.narrative),
            educational = adjustToneValue(tone.educational)
        )
    }
}
