package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.generativecontentservice.model.enums.ComponentType
import com.modernlibrary.generativecontentservice.model.enums.ContentStatus

data class GeneratedContentDetailsDto(
    val requestId: String,
    val componentReferenceId: String,
    val generativeReferenceId: String,
    val templateReferenceId: String,
    val componentType: ComponentType,
    val status: ContentStatus,
)
