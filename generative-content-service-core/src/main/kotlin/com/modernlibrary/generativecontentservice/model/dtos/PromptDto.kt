package com.modernlibrary.generativecontentservice.model.dtos

data class PromptDto(
    val templateReferenceId: String,
    val systemRole: String,
    val promptObjective: String,
    val model: String,
    val userDerivedInformation: String,
    val outputStyle: String,
    var finalPrompt: String? = null,
)
