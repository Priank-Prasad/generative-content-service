package com.modernlibrary.generativecontentservice.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.LocalDateTime

abstract class BaseEntity(
    @Id open var id: Long? = null,
    @CreatedDate open var createdAt: LocalDateTime? = null,
    @LastModifiedDate open var updatedAt: LocalDateTime? = null,
    @Version open var version: Int? = null,
)
