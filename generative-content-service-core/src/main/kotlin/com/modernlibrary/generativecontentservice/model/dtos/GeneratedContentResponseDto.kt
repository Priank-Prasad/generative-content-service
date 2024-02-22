package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.common.client.model.ContentTone


data class GeneratedContentResponseDto(
    val content: String,
    val contentTone: ContentTone,
    val contentDetails: GeneratedContentDetailsDto,
)