package com.modernlibrary.generativecontentservice.service

import com.modernlibrary.generativecontentservice.model.dtos.GenerateContentRequestDto
import com.modernlibrary.generativecontentservice.model.dtos.GeneratedContentResponseDto

interface ContentGeneratorService {

    suspend fun generateMultipleContents(requestDtoList: List<GenerateContentRequestDto>): List<GeneratedContentResponseDto>
}