package com.modernlibrary.generativecontentservice.model.entity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.modernlibrary.contentorchestratorservice.client.contentPlatform.GenerateComponentInternalRequest
import com.modernlibrary.contentorchestratorservice.client.model.CreateContentRequest
import com.modernlibrary.generativecontentservice.model.enums.RdtRequestType
import com.modernlibrary.generativecontentservice.model.enums.RdtStatus
import org.springframework.data.relational.core.mapping.Table

@Table("request_details_tracker")
data class RequestDetailTracker (
    val requestId: String,
    val requestType: RdtRequestType,
    var rdtStatus : RdtStatus,
    val payload: String,
    var errorReason: String? = null,
): BaseEntity() {

    constructor(request: GenerateComponentInternalRequest): this(
        requestId = request.requestId,
        requestType = RdtRequestType.GENERATE_CONTENT,
        rdtStatus = RdtStatus.IN_PROGRESS,
        payload =  jacksonObjectMapper().writeValueAsString(request)
    )
}
