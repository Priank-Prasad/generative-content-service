package com.modernlibrary.generativecontentservice.model.entity

import org.springframework.data.relational.core.mapping.Table


@Table("prompt")
data class Prompt(
    val templateReferenceId: String,
    val promptObjective: String,
    val model: String,
    val description: String, // Prompt creator should add a description
    val systemRole: String,
    val userDerivedInformation: String,  // Will contain all the replaceable values
    val outputStyle: String, // paramDefinition & moderation will be constant across all templates
): BaseEntity()
