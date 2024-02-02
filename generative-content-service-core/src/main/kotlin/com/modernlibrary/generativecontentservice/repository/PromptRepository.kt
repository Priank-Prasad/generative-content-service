package com.modernlibrary.generativecontentservice.repository

import com.modernlibrary.generativecontentservice.model.entity.Prompt
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono


@Repository
interface PromptRepository: R2dbcRepository<Prompt, Long> {
    fun findByTemplateReferenceId(templateReferenceId: String): Mono<Prompt>
}
