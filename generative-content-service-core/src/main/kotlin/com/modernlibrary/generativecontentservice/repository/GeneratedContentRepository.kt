package com.modernlibrary.generativecontentservice.repository

import com.modernlibrary.generativecontentservice.model.entity.GeneratedContent
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository


@Repository
interface GeneratedContentRepository: R2dbcRepository<GeneratedContent, Long> {
}