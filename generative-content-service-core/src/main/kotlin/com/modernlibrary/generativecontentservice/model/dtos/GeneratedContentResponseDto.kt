package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.contentorchestratorservice.client.model.ContentTone

data class GeneratedContentResponseDto(
    val content: String,
    val contentTone: ContentTone,
    val contentDetails: GeneratedContentDetailsDto,
)