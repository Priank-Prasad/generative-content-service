package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.common.client.model.ContentTone
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerativeComponentConfigs
import com.modernlibrary.generativecontentservice.model.enums.ComponentType


data class CreateContentRequestDto(
    val requestId: String,
    val componentReferenceId: String,
    val templateReferenceId: String,
    val componentType: ComponentType,
    val tone: ContentTone,
    val contentConfig: GenerativeComponentConfigs,
)
