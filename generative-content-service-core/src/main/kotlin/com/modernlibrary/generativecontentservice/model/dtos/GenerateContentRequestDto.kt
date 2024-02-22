package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.common.client.model.ContentTone
import com.modernlibrary.common.client.model.InferredUserContentDetails
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerativeComponentConfigs

data class GenerateContentRequestDto(
    val contentDetails: GeneratedContentDetailsDto,
    val contentTone: ContentTone,
    val userDetails: InferredUserContentDetails,
    val configs: GenerativeComponentConfigs,
)
