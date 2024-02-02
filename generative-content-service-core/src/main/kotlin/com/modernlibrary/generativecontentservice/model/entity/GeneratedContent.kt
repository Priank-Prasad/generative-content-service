package com.modernlibrary.generativecontentservice.model.entity

import com.modernlibrary.generativecontentservice.model.enums.ComponentType
import com.modernlibrary.generativecontentservice.model.enums.ContentStatus

data class GeneratedContent(
    val requestId: String,
    val componentReferenceId: String,
    val generativeReferenceId: String,
    val templateReferenceId: String,
    val componentType: ComponentType,
    val status: ContentStatus,
): BaseEntity()
