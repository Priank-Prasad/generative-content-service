package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.generativecontentservice.model.enums.ComponentType


data class CreateContentRequestDto(
    val requestId: String,
    val componentReferenceId: String,
    val templateReferenceId: String,
    val componentType: ComponentType,
)
