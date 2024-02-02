package com.modernlibrary.generativecontentservice.model.entity


data class Prompt(
    val templateReferenceId: String,
    val promptObjective: String,
    val model: String,
    val description: String, // Prompt creator should add a description
    val systemRole: String,
    val inferredExtraInformation: String,  // Will contain all the replaceable values
    val outputStyle: String, // paramDefinition & moderation will be constant across all templates
): BaseEntity()
