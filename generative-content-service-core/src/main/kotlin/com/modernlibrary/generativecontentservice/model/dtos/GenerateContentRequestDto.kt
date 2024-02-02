package com.modernlibrary.generativecontentservice.model.dtos

import com.modernlibrary.contentorchestratorservice.client.model.ComponentConfigs
import com.modernlibrary.contentorchestratorservice.client.model.ContentTone
import com.modernlibrary.contentorchestratorservice.client.model.UserData

data class GenerateContentRequestDto(
    val contentDetails: GeneratedContentDetailsDto,
    val contentTone: ContentTone,
    val userDetails: UserData,
    val configs: ComponentConfigs,
)
